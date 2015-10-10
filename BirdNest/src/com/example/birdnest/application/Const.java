package com.example.birdnest.application;

public interface Const 
{
	  /*  	//用户类型
    SUPE_RUSER = 0x00000000    // 超级用户 全部 可发可删
    DEPARTMENT_CHIEF = 0x00000001 //部长 本部门 可发 可删 
    NORMAL  = 0x00000002    //普通 只读
*/
 /*   消息类型  MSG_TYPE 
    NEWS_TYPE  = 0x00000010 　//新闻
    NOTICE_TYPE = 0x00000011  //通知
    PRR_NOTICE = 0x00000012  //活动预告
*/
    //部门类别：DEPAT_TYPE
	 public static final String  PRESENDENT_DEPT = "主席团"  ;//0x00000100  //主席团
	 public static final String  COMMITTEE_DEPT = "常委" ; 
	 public static final String  ORGNISE_DEPT ="组织部" ;  
 	 public static final String  MEDIA_DEPT = "新媒体中心";//0x00000102 
	 public static final String  ART_DEPT = "文艺部" ;// 0x00000103 //
	 public static final String  PRACTICE_DEPT =  "社会实践部" ;//  0x00000104 //
 	 public static final String  EX_CONECT_DEPT =  "对外联络部" ; // 0x00000105 //外联部
	 public static final String  LIFE_DEPT = "生活部服务部" ; // 0x00000106 //生活部
	 public static final String  DOCTOR_DEPT = "博士生部" ; // 0x00000107 //博士部
	 public static final String  POINEER_DEPT = "创业就业部";// 0x00000108 //创业部
	 public static final String  SURVEY_DEPT = "调查研究部";//0x00000109 //调查部
	 public static final String  SPORT_DEPT = "体育部"; //0x0000010A //
	 public static final String  INTERNATION_DEPT ="国际交流与合作部";// 0x0000010B //国际部
	 public static final String  WEST_CHINA_DEPT = "华西工作部" ;// 0x0000010C //华西工作部
	 public static final String  JIANGAN_DEPT = "江安工作部" ;//0x0000010D //江安工作部
	 public static final String  SUNDAY_MAGZINE_DEPT = "《星期日》杂志社"; // 0x0000010E //星期日
 	 public static final String  POSTGRD_NEWSPAPER_DEPT = "《川大研究生》报社"; //0x0000010F  //研报
	 public static final String  ACADEMIC_DEPT = "学术科技部"; //0x00000110 //学术部
	 public static final String  SECRETARY_DEPT = "秘书处"; //0x00000111 //秘书处
	 public static final String  PUBLICITY_DEPT = "宣传部" ; //0x00000112  //宣传部
	 public static final String  ADVISER_TEACHER = "研究生工作部" ;  // 0x00000113  //指导老师
	 public static final String CAMERA_PHOTO_DIR = "BirdNest/CurrentUser/image/camera";
}
