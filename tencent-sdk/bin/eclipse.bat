@echo off
@cd ..
call mvn eclipse:eclipse -DdownloadSources=true -U
@pause