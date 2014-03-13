package com.tencent.weibo.test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpHost;

import com.tencent.weibo.api.UserAPI;
import com.tencent.weibo.beans.RouteCfg;
import com.tencent.weibo.oauthv1.OAuthV1;
import com.tencent.weibo.oauthv1.OAuthV1Client;
import com.tencent.weibo.utils.QHttpClient;

/**
 * 连接管理器QHttpClient配置示例
 *
 */
public class TestQHttpClient {

    private static OAuthV1 oAuth=new OAuthV1();

	public static void main(String[] args) {
	    
	    /*
	     * 初始化 OAuth ，设置 app key 和 对应的 secret
	     * 重复测试时，可直接对access token等鉴权参数赋值，以便省略授权过程（完成一次正常的授权过程即可手动设置）
	     */
	    init(oAuth); 
	    
/*
 * -----------------------------------------授权流程 begin-------------------------------------------------- 
*/

	    //自定制http连接管理器
	    QHttpClient qHttpClient=new QHttpClient(2, 2, 1000, 1000, null, null);
	    OAuthV1Client.setQHttpClient(qHttpClient);
	    
       // 获取request token
       try {
           oAuth = OAuthV1Client.requestToken(oAuth);
       } catch (Exception e1) {
           e1.printStackTrace();
       }

       //检查是否正确取得request token
       if (oAuth.getStatus() == 1) {
           System.out.println("Get Request Token failed!");
           return;
       }

       //调用外部浏览器，请求用户授权
       openBrowser(oAuth);     
       
       //读入授权码等参数
       try {
           oAuth = verify(oAuth);
       } catch (Exception e2) {
           e2.printStackTrace();
       }    
       
       //检查是否正确取得授权码
       if (oAuth.getStatus() == 2) {
           System.out.println("Get Authorization Code failed!");
           return;
       }
       
       //换取access token
        try {
            oAuth = OAuthV1Client.accessToken(oAuth);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        
       //检查是否正确取得access token
       if (oAuth.getStatus() == 3) {
            System.out.println("Get Access Token failed!");
            return;
        }
       
       qHttpClient.shutdownConnection();
/*
 * -----------------------------------------授权流程 end-------------------------------------------------- 
 */        

	    //执行测试
		try {
			new TestQHttpClient().test();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	 // --------------------------测试多线程并发操作、特殊路由的连接数配置和代理设置 begin--------------------------

	private void test() throws Exception {

	    //设置特殊host的最大连接数
	    List<RouteCfg> routeCfgList=null;
//	    routeCfgList=new ArrayList<RouteCfg>();
//        RouteCfg routeCfg=new RouteCfg("183.62.125.8",80,4);
//        RouteCfg routeCfg2=new RouteCfg("113.108.90.18",80,2);
//        routeCfgList.add(routeCfg);
//        routeCfgList.add(routeCfg2);
        
	    //设置代理
        HttpHost proxy=null;
//        proxy=new HttpHost("proxy.tencent.com", 8080);
        
	    QHttpClient testQHttpClient=new QHttpClient(3, 6, 2000, 2000, routeCfgList, proxy);
        UserAPI userAPI= new UserAPI(oAuth.getOauthVersion(),testQHttpClient);
            
        TestThread[] threads = new TestThread[15];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new TestThread(userAPI, oAuth,i);
        }

        // start the threads
        for (int j = 0; j < threads.length; j++) {
            threads[j].start();
        }

        // join the threads
        for (int j = 0; j < threads.length; j++) {
            threads[j].join();
        }
        
        testQHttpClient.shutdownConnection();
	}

 // ---------------------------测试多线程并发操作、特殊路由的连接数配置和代理设置 end---------------------------

	
	
//---------------------------------------------- 以下为测试辅助方法------------------------------------------------	
	
    private static OAuthV1 verify(OAuthV1 oAuth) throws Exception {
        System.out.println("Input the authorization information (eg: oauth_token=OAUTH_TOKEN&vcode=VERIFY_CODE&openid=OPENID&openkey=OPENKEY)：");
        Scanner in = new Scanner(System.in);
        String tmpstr = in.nextLine(); 
        
        //获取access token
        System.out.println("Getting Access Token......");
        OAuthV1Client.parseAuthorization(tmpstr,oAuth);
        
        in.close();
        return oAuth;
    }

    private static void init(OAuthV1 oAuth) {
        oAuth.setOauthConsumerKey("801115505");
        oAuth.setOauthConsumerSecret("be1dd1410434a9f7d5a2586bab7a6829");
//        oAuth.setOauthCallback("");
        oAuth.setOauthToken("af51069722f04c15911cdf25b91bfe58");
        oAuth.setOauthTokenSecret("81a98d6117ad686eb3aad86061707e4a");
    }

    private static void openBrowser(OAuthV1 oAuth) {
		
		String authorizationUrl = OAuthV1Client.generateAuthorizationURL(oAuth);
	
        System.out.println("Get verification code......");
        if( !java.awt.Desktop.isDesktopSupported() ) {

            System.err.println( "Desktop is not supported (fatal)" );
            System.exit( 1 );
        }
        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
        if(desktop == null || !desktop.isSupported( java.awt.Desktop.Action.BROWSE ) ) {

            System.err.println( "Desktop doesn't support the browse action (fatal)" );
            System.exit( 1 );
        }
        try {
			desktop.browse(new URI(authorizationUrl));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit( 1 );
		} catch (URISyntaxException e) {
			e.printStackTrace();
			System.exit( 1 );
		}
    }
}

/*
 * 测试线程
 */
class TestThread extends Thread {

    private UserAPI userAPI;
    private OAuthV1 oAuth;
    private int num;
    
    public TestThread(UserAPI userAPI,OAuthV1 oAuth ,int num) {
        this.userAPI = userAPI;
        this.oAuth = oAuth;
        this.num=num;
    }
    
    @Override
    public void run() {
        try {
//            System.out.println("------------Thread "+num+" is starting !------------");
            String response=userAPI.info(oAuth, "json");
            if(response.indexOf("\"msg\":\"ok\"")!=-1)
                System.out.println("------------Thread "+num+" has done !------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
}