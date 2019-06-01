@echo off
@title server Command Central
java -Xmx850m -cp bin;data/libs/*;data/libs/javamail-1.4.5/mail.jar;lib/*; com.rs.Launcher false false true
