package javaio;
import java.io.*;
public class UseIO {

    BufferedReader readIn(String fileDir) throws IOException {
    	String[] fileDirArr=fileDir.split("\\\\");
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<fileDirArr.length;i++){
			if(i<fileDirArr.length-1){
				sb.append(fileDirArr[i]);
				sb.append("\\");
			}else{
				sb.append(fileDirArr[i]);
			}
		}
		File file=new File(sb.toString());
		BufferedReader fileBr=new BufferedReader(new FileReader(file));
		return fileBr;
    }

    void numCal(BufferedReader fileBr, int mode) throws IOException {
		String temp;
		int numEng=0;
		int numCh=0;
		int numSum=0;
		while((temp=fileBr.readLine())!=null){
			for(int i=0;i<temp.length();i++){
				char tempChar=temp.charAt(i);
				if(tempChar>96&&tempChar<123||tempChar>64&&tempChar<91){
					numEng++;
				}
				if(tempChar>19967&&tempChar<40870){
					numCh++;
				}
			}
			numSum++;
		}
		
		if(mode == 1){
			File file=new File("out.txt");
			BufferedWriter bf=new BufferedWriter(new PrintWriter(file));
			bf.write("字符串有 "+numSum+" 个");
			bf.newLine(); 
			bf.write("英文字母有 "+numEng+" 个");
			bf.newLine(); 
			bf.write("中文字母有 "+numCh+" 个");
			bf.close();
			System.out.println("已将结果写入文件。");
		}
		if(mode == 2){
			System.out.println("字符串有 "+numSum+" 个");
			System.out.println("英文字母有 "+numEng+" 个");
			System.out.println("中文字母有 "+numCh+" 个");
		}
	}
}
