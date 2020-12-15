#include <unistd.h> 
#ifndef _TOOLS_H_ 
#define _TOOLS_H_ 
#define SOCKET_ERROR -1

/**SMTP settings**/
#define SMTP_SERVER "smtp.163.com"
#define SMTP_IP "123.126.97.2"
#define SMTP_PORT 25

/**IMAP settings**/
#define IMAP_SERVER "imap.163.com"
#define IMAP_IP "123.126.97.78"
#define IMAP_PORT 143

char buffer[1024];
int sockfd,ret;
char const *send_data;

int connectHost(const char *addr,const char *ipstr,int port){
    struct sockaddr_in servaddr;
    sockfd = socket(AF_INET,SOCK_STREAM,0);
    if(sockfd < 0){
        printf("Create socket error!\n");
        return -1;
    }
    bzero(&servaddr,sizeof(servaddr));
    servaddr.sin_family = AF_INET;
    servaddr.sin_port = htons(port);
    
    if(inet_pton(AF_INET, ipstr, &servaddr.sin_addr) <= 0 ){
        printf("inet_pton error!\n");
        return -1;
    };

    if (connect(sockfd,(struct sockaddr *)&servaddr,sizeof(servaddr)) < 0){
        printf("Connect failed... \n");
        return -1;
    }
     
    memset(buffer, 0, sizeof(buffer));
     
    if(recv(sockfd, buffer, sizeof(buffer), 0) < 0){    
        printf("receive failed... \n");
        return -1;
    }
    return sockfd;    
}

int getResponse(){    
    memset(buffer,0,sizeof(buffer));
    ret = recv(sockfd,buffer,1024,0);
    if(ret == SOCKET_ERROR){
        printf("receive nothing\n");
        return -1;
    }
    buffer[ret]='\0';
     
    if(*buffer == '5'){
        printf("the order is not support smtp host，%s\n ",buffer);
        return -1;
    }else if (*buffer == '-'){
        printf("the order is not support pop host，%s\n ",buffer);
        return -1;
    }
    //printf("%s\n", buffer);
    return 0;
}

int login(char* username,char* password){
    char ch[100];
    if(username == "" || password == ""){
        return -1;
    }
     
    send_data = "HELO 163.com\r\n";
    ret = send(sockfd, send_data, strlen(send_data), 0);
    if(ret == SOCKET_ERROR){
        return -1;
    }         
    if(getResponse() < 0){
        return -1;
    }
     
    send_data = "AUTH LOGIN\r\n";
    ret = send(sockfd, send_data, strlen(send_data), 0);
    if(ret == SOCKET_ERROR){
        return -1;
    }
    if(getResponse() < 0){
        return -1;
    }

    sprintf(ch, "%s\r\n", username);
    ret = send(sockfd, (char *)ch, strlen(ch),0);
    if(ret == SOCKET_ERROR){
        return -1;
    }
     
    if(getResponse() < 0){
         return -1;
    }
     
    sprintf(ch,"%s\r\n",password);
    ret = send(sockfd,(char *)ch,strlen(ch),0);
    if(ret == SOCKET_ERROR){
        return -1;
    }
     
    if(getResponse() < 0){
        return -1;
    }
    return 0;
 
}

void watch_help(){
	//查看帮助
	printf("---------------欢迎查看帮助文档---------------\n");
    printf("            email inbox进入收件箱\n");
    printf("            email -h查看帮助文档\n");
    printf("            email setuser设定用户\n");
    printf("            email send 目标邮箱 发送邮件\n");
}

void getNamePasswd(char name[],char passwd[]){
    FILE* fp = fopen("email.conf","r");
    if (fp != NULL){
        //配置文件存在,从配置文件中读取base64加密过的用户名密码
        fscanf(fp,"%s\n%s",name,passwd);
    }else{
        //配置文件不存在，进入配置模块
        printf("请先设定用户和密码--email setuser\n");
        exit(0);
    }
}

const char base[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

/* base64 编码 */
char *base64_encode(const char* data){   
    int data_len = strlen(data);   
    int prepare = 0;   
    int ret_len;   
    int temp = 0;   
    char *ret = NULL;   
    char *f = NULL;   
    int tmp = 0;   
    char changed[4];   
    int i = 0;   
    ret_len = data_len / 3;   
    temp = data_len % 3;   
    if (temp > 0){   
        ret_len += 1;   
    }   
    ret_len = ret_len*4 + 1;   
    ret = (char *)malloc(ret_len);   
        
    if ( ret == NULL){   
        printf("No enough memory.\n");   
        exit(0);   
    }   
    memset(ret, 0, ret_len);   
    f = ret;   
    while (tmp < data_len){   
        temp = 0;   
        prepare = 0;   
        memset(changed, '\0', 4);   
        while (temp < 3){      
            if (tmp >= data_len){   
                break;   
            }   
            prepare = ((prepare << 8) | (data[tmp] & 0xFF));   
            tmp++;   
            temp++;   
        }   
        prepare = (prepare<<((3-temp)*8));      
        for (i = 0; i < 4 ;i++ ){   
            if (temp < i){   
                changed[i] = 0x40;   
            }else{   
                changed[i] = (prepare>>((3-i)*6)) & 0x3F;   
            }   
            *f = base[changed[i]];   
            f++; 
        }   
    }   
    *f = '\0';   
        
    return ret;   
        
}   

/* 转换算子 */   
static char find_pos(char ch){   
    char *ptr = (char*)strrchr(base, ch);//the last position (the only) in base[]   
    return (ptr - base);   
}   
  
/* Base64 解码 */   
char *base64_decode(const char *data){   
	int data_len = strlen(data); 
    int ret_len = (data_len / 4) * 3;   
    int equal_count = 0;   
    char *ret = NULL;   
    char *f = NULL;   
    int tmp = 0;   
    int temp = 0;  
    int prepare = 0;   
    int i = 0;   
    if (*(data + data_len - 1) == '='){   
        equal_count += 1;   
    }   
    if (*(data + data_len - 2) == '='){   
        equal_count += 1;   
    }   
    if (*(data + data_len - 3) == '='){  
        equal_count += 1;   
    }   
    switch (equal_count){   
    case 0:   
        ret_len += 4;//3 + 1 [1 for NULL]   
        break;   
    case 1:   
        ret_len += 4;//Ceil((6*3)/8)+1   
        break;   
    case 2:   
        ret_len += 3;//Ceil((6*2)/8)+1   
        break;   
    case 3:   
        ret_len += 2;//Ceil((6*1)/8)+1   
        break;   
    }   
    ret = (char *)malloc(ret_len);   
    if (ret == NULL){   
        printf("No enough memory.\n");   
        exit(0);   
    }   
    memset(ret, 0, ret_len);   
    f = ret;   
    while (tmp < (data_len - equal_count)){   
        temp = 0;   
        prepare = 0;   
        while (temp < 4){   
            if (tmp >= (data_len - equal_count)){   
                break;   
            }   
            prepare = (prepare << 6) | (find_pos(data[tmp]));   
            temp++;   
            tmp++;   
        }   
        prepare = prepare << ((4-temp) * 6);   
        for (i=0; i<3 ;i++ ){   
            if (i == temp){   
                break;   
            }   
            *f = (char)((prepare>>((2-i)*8)) & 0xFF);   
            f++;   
        }   
    }   
    *f = '\0';   
    return ret;   
} 

//设置用户信息
void setUser(){
    printf("请输入账号:");
    char name[100],*passwd;
    scanf("%s",name);
    getchar();
    passwd = getpass("请输入密码:");
    char* base64_name = base64_encode(name);
    char* base64_passwd = base64_encode(passwd);
    FILE* fp = fopen("email.conf","w");
    fprintf(fp, "%s\n%s",base64_name,base64_passwd);
    fclose(fp);
}


#endif
