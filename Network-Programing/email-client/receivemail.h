#ifndef _RECEIVEMAIL_H_ 
#define _RECEIVEMAIL_H_
#include "tools.h"

int watch_inbox(){
	//连接服务器,参数，服务器地址，ip地址，端口号
	if(connectHost(IMAP_SERVER, IMAP_IP, IMAP_PORT)<0){
        return -1;
    }

    //登录
    char ch[1024];
	char name[100];
    char passwd[100];
    getNamePasswd(name,passwd);

    sprintf(ch, "a001 LOGIN %s %s\r\n",base64_decode(name),base64_decode(passwd));
    ret = send(sockfd, (char *)ch, strlen(ch),0);
    if(ret == SOCKET_ERROR){
        return -1;
    }
    if(getResponse() < 0){
         return -1;
    }

    sprintf(ch, "a002 SELECT INBOX\r\n");
    ret = send(sockfd, (char *)ch, strlen(ch),0);
    if(ret == SOCKET_ERROR){
        return -1;
    }
    if(getResponse() < 0){
         return -1;
    }

    char* p = strtok(buffer," \n");
    p = strtok(NULL," \n");
    int total = atoi(p);
    if(total == 0){
    	printf("邮箱为空！\n");
    	return -1;
    }

    for (int i=0;i<total;++i){
		
		sprintf(ch, "a003 fetch %d FLAGS(SEEN)\r\n",i+1);
    	ret = send(sockfd, (char *)ch, strlen(ch),0);
   		if (ret == SOCKET_ERROR){
    		return -1;
    	}
    	if(getResponse() < 0){
        	return -1;
    	}	
    	p = strtok(buffer,"\n");
    	char* q = strtok(p," ");
    	q = strtok(NULL," ");
    	q = strtok(NULL," ");
    	q = strtok(NULL," ");
    	q = strtok(NULL," ");
    	q = strtok(q,")");
		printf("第%d封邮件 %s):\n", i+1,q);
		
		sprintf(ch, "a003 fetch %d BODY[HEADER.FIELDS (DATE FROM TO SUBJECT)]\r\n",i+1);
    	ret = send(sockfd, (char *)ch, strlen(ch),0);
   		if (ret == SOCKET_ERROR){
    		return -1;
    	}
    	if(getResponse() < 0){
        	return -1;
    	}
    	q = strtok(buffer,"\n");
    	q = strtok(NULL,"\n");
    	printf("%s\n",q);
    	q = strtok(NULL,"\n");
    	printf("%s\n",q);
    	q = strtok(NULL,"\n");
    	printf("%s\n",q);
    	q = strtok(NULL,"\n");
    	printf("%s\n",q);
    	
    	sprintf(ch, "a003 fetch %d BODY[1]\r\n",i+1);
    	ret = send(sockfd, (char *)ch, strlen(ch),0);
   		if (ret == SOCKET_ERROR){
    		return -1;
    	}
    	if(getResponse() < 0){
        	return -1;
    	}
    	p = strtok(buffer,"\n");
		p = strtok(NULL,"\n");
    	//printf("Content:\n%s\n",p);
    	printf("Content:\n%s\n", base64_decode((const char *)p));
    	printf("-------------------------------------\n");
    }
	printf("是否删除邮件？[y/n]\n");
	char del;
	scanf("%c",&del);
	if(del == 'y'){
		printf("请输入要删除邮件的编号:");
		int delete_i;
		scanf("%d",&delete_i);
		while(delete_i > total){
			printf("输入有误，请重新输入。\n");
			printf("请输入要删除邮件的编号:");
			scanf("%d",&delete_i);
		}
		sprintf(ch, "a004 STORE %d +flags (\\Deleted)\r\n",delete_i);
		ret = send(sockfd, (char *)ch, strlen(ch),0);
		if (ret == SOCKET_ERROR){
			return -1;
		}
		if(getResponse() < 0){
			return -1;
		}
		printf("删除成功！\n");
	}
}

#endif
