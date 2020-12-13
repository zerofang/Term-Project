这个邮件收发程序只能在Linux系统上编译运行，因为用的库是Linux的socket编程库。  
在编译运行之前，你需要先准备好Linux系统/虚拟机，确保你已经安装了GCC编译工具，如果没有，使用以下命令安装GCC：  
sudo apt install build-essential  

1.在命令行打开程序所在文档  
2.编译程序  
   gcc email.cpp -o email  
3.执行程序  
   ./email    后接不同参数执行不同功能 无参数时显示帮助文档  
4.设置用户  
./email setuser  
5.编写和发送邮件  
./email send  
此处回车发送  
6.进入收件箱  
./email inbox  
