# ATMandBank
###  C/S模式的模拟ATM取款机
- **项目介绍**：  
独立开发  
网络计算课学期项目，包括ATM机端提供的登录，存取款，转账，打印凭条功能，配套的银行业务系统和用户业务数据库等，考虑了多线程处理，服务中途出错的业务回滚处理以及安全验证问题。   
语言和工具：Java，SQL，Access，WindowBuilder，UCanAccess
- **使用说明**：  
ATM和Bank是两个project，重构后ATM项目src目录下只有ATM.Java是有效代码，其他只是辅助编写界面使用。  
先运行Bank.java以开启服务器，然后运行ATM.java即可开启客户端并进行各项操作。  
- **开发状态**：  
2018.5.3 所有功能完成，通信加密采用SSL加密，服务器端和客户端证书互相授权。
基本功能（ATM机端提供的登录，存取款，转账，打印凭条功能，配套的银行业务系统和用户业务数据库等，服务器端多线程处理，基本的安全验和网络出错处理等）均已完成，目前待完成业务回滚和完善安全验证。
