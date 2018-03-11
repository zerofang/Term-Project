#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <netdb.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <sys/fcntl.h>
#include <sys/stat.h>
#include <errno.h>
#include "sendmail.h"
#include "receivemail.h"

int main(int argc,char* argv[]){

	if (argc == 1){
		//如果直接输入mail的话，直接进入收件箱
		watch_help();
	}else{
		//判断mail命令之后的参数
		if (strcmp(argv[1],"setuser") == 0){
			//-user
			setUser();
		}else if (strcmp(argv[1],"send") == 0){
			//发送邮件,接受参数要发送到的账号
			char* to = argv[2];
			if (to == NULL){
				printf("地址为空！\n");
				return -1;
			}
			send_mail(to);
		}else if (strcmp(argv[1],"-h") == 0){
			watch_help();
		}else if (strcmp(argv[1],"inbox") == 0){
			watch_inbox();
		}else{
			printf("输入选项错误，请查看帮助，命令为-h\n");
			return -1;
		}
	}
	return 0;
}
