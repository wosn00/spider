import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\hs\\Desktop\\计组简答题.txt");
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line;
        String result = "";
        while ((line = in.readLine()) != null) {
            result += line + "\n";
        }
        Pattern compile = Pattern.compile("~~[\\s\\S]+?(?=~~)");
        Pattern title = Pattern.compile("\\^\\^([\\s\\S]+)\\^\\^");
        Pattern answer = Pattern.compile("\\^\\^[\\s\\S]+\\^\\^([\\s\\S]+)$");

        Matcher m = compile.matcher(result);
        List<Summary> list = new ArrayList<Summary>();
        while (m.find()) {
            Summary summary = new Summary();
            //完整题
            String group = m.group();
            //题目
            Matcher title1 = title.matcher(group);
            if (title1.find()) {
                summary.setTitle(title1.group(1).replaceAll("\n", ""));
            }
            //答案
            Matcher answer1 = answer.matcher(group);
            if (answer1.find()) {
                String answer2 = answer1.group(1).replaceAll("\n", "");
                summary.setAnswer(answer2);
                //添加关键词
                boolean flag1 = answer2.contains(",");
                boolean flag2 = answer2.contains("。");
                boolean flag3 = answer2.contains("(");
                boolean flag4 = answer2.contains("（");
                if (!(flag1||flag2||flag3||flag4)) {
                    String[] s = answer2.split(" ");
                    String keyword = "";
                    for (int i = 0; i < s.length; i++) {
                        if (s.length != 1) {
                            keyword += s[i] + "；";
                        } else {
                            keyword += s[i];
                        }
                    }
                    summary.setKeyword(keyword);
                }
            }
            //课程
            summary.setCourse("1");
            //章节
            String substring = group.substring(5, 7);
            Integer chapter = Integer.valueOf(substring);
            if (chapter == 1) {
                summary.setChapter("1");
            }
            if (chapter == 2 || chapter == 3) {
                summary.setChapter("2");
            }
            if (chapter == 4 || chapter == 5 || chapter == 10) {
                summary.setChapter("3");
            }
            if (chapter == 6) {
                summary.setChapter("4");
            }
            if (chapter == 7) {
                summary.setChapter("5");
            }
            if (chapter == 8) {
                summary.setChapter("6");
            }


            list.add(summary);
        }
        //工具类
        ExportExcel<Summary> ex = new ExportExcel<Summary>();
        String[] headers = {"course", "chapter", "title", "answer", "keyword"};
        //时间格式化
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        //文件名
        String fileName = "自动导出简答题.xls";
        String path = "C:\\Users\\hs";
        //文件路径
        path = path + File.separator + fileName;
        File file1 = new File(path);
        //输出流
        FileOutputStream out = new FileOutputStream(file1);
        //实例化Excel表格
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建工作表单
        String[] sheetNames = { "计组简答题" };
        //实例化sheet
        workbook.createSheet(sheetNames[0]);
        //导出到Excel
        ex.exportExcel(sheetNames[0], headers, list, out,
                "yyyy-MM-dd HH:mm", workbook);
        //保存文件
        workbook.write(out);
        out.close();


    }


//    public static void main(String[] args) throws IOException {
//        File file = new File("C:\\Users\\hs\\Desktop\\计组新.txt");
//        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
//        String line;
//        String result = "";
//        while ((line = in.readLine()) != null) {
//            result += line + "\n";
//        }
//        Pattern compile = Pattern.compile("~~[\\s\\S]+?(?=~~)");
//        Pattern title = Pattern.compile("(\\^\\^[\\s\\S]+?)A");
//        Pattern abcd = Pattern.compile("([A-D]、[\\s\\S]+?)(?=[A-D]|^)");
//        Pattern answer=Pattern.compile("[A-D]$");
//
//        Matcher m = compile.matcher(result);
//
//        List<Entity> list = new ArrayList<Entity>();
//        while (m.find()) {
//            Entity entity = new Entity();
//            String group = m.group();
//            //题目
//            Matcher title1 = title.matcher(group);
//            if (title1.find()) {
//                entity.setTitle(title1.group(1).replaceAll("\\^","").replaceAll("\n",""));
//            }
//            //答案
//            Matcher answer1 = answer.matcher(group);
//            String answer2="";
//            if (answer1.find()){
//                answer2 = answer1.group().trim();
//            }
//            char c = answer2.charAt(0);
//            entity.setAnswer(c);
//            //ABCD
//            Matcher abcd1 = abcd.matcher(group);
//            for (int i = 1; i < 5; i++) {
//                if (abcd1.find()) {
//                    if (i == 1) {
//                        entity.setA(abcd1.group(1).replaceAll("\n", ""));
//                    }
//                    if (i == 2) {
//                        entity.setB(abcd1.group(1).replaceAll("\n", ""));
//                    }
//                    if (i == 3) {
//                        entity.setC(abcd1.group(1).replaceAll("\n", ""));
//                    }
//                    if (i == 4) {
//                        entity.setD(abcd1.group(1).replaceAll("\n", "").replaceAll("\\^",""));
//                    }
//                }
//            }
//            //章节
//            String substring = group.substring(5, 7);
//            Integer chapter = Integer.valueOf(substring);
//            if (chapter==1){ entity.setChapter("1");}
//            if (chapter==2||chapter==3){ entity.setChapter("2");}
//            if (chapter==4||chapter==5||chapter==10){ entity.setChapter("3");}
//            if (chapter==6){ entity.setChapter("4");}
//            if (chapter==7){ entity.setChapter("5");}
//            if (chapter==8){ entity.setChapter("6");}
//            //课程
//            entity.setCourse("1");
//
//            list.add(entity);
//        }
//        //工具类
//        ExportExcel<Entity> ex = new ExportExcel<Entity>();
//        String[] headers = {"course", "chapter", "title", "a", "b", "c", "d", "answer"};
//        //时间格式化
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
//        //文件名
//        String fileName = "自动导出365.xls";
//        String path = "C:\\Users\\hs";
//        //文件路径
//        path = path + File.separator + fileName;
//        File file1 = new File(path);
//        //输出流
//        FileOutputStream out = new FileOutputStream(file1);
//        //实例化Excel表格
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        //创建工作表单
//        String[] sheetNames = { "计组选择题" };
//        //实例化sheet
//        workbook.createSheet(sheetNames[0]);
//        //导出到Excel
//        ex.exportExcel(sheetNames[0], headers, list, out,
//                "yyyy-MM-dd HH:mm", workbook);
//        //保存文件
//        workbook.write(out);
//        out.close();
//
//
//
//
//
//    }

//    public static void main(String[] args) throws IOException {
//        File file = new File("C:\\Users\\hs\\Desktop\\计组85.txt");
//        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
//        String result = "";
//        String line;
//        while ((line = in.readLine()) != null) {
//            result += line+"\n";
//        }
////        System.out.println(result);
//        //完整题
//        Pattern compile = Pattern.compile("[0-9]+\\.[\\s\\S]+?(?=[0-9]+\\.\\s)");
//        //题目
//        Pattern title = Pattern.compile("([0-9]+\\.[\\s\\S]+)A\\.\\s");
//        Matcher m = compile.matcher(result);
//        //答案ABCD
//        Pattern abcd = Pattern.compile("[A-D]\\.\\s.+\\n");
//        List<Entity> entities = new ArrayList<Entity>();
//
//        while (m.find()){
//            Entity entity = new Entity();
//            entity.setCourse("1");
//            entity.setChapter("1");
//            //整题
//            String group = m.group();
////            System.out.println(group);
//            //题目
//            Matcher title1 = title.matcher(group);
//            if (title1.find()) {
//                entity.setTitle(title1.group(1).replaceAll("\n",""));
//                System.out.println(title1.group(1));
//            }
//            Matcher matcher = abcd.matcher(group);
//            for (int i = 1; i < 5; i++) {
//                if (matcher.find()) {
//                    if (i == 1) {
//                        entity.setA(matcher.group(0).replaceAll("\n",""));
//                    }
//                    if (i == 2) {
//                        entity.setB(matcher.group(0).replaceAll("\n",""));
//                    }
//                    if (i == 3) {
//                        entity.setC(matcher.group(0).replaceAll("\n",""));
//                    }
//                    if (i == 4) {
//                        entity.setD(matcher.group(0).replaceAll("\n",""));
//                    }
//                }
//            }
//            System.out.println("----------------------");
//
//            entities.add(entity);
//        }
//        System.out.println(entities);
//        String answer = getAnswer();
//        char[] chars = answer.toCharArray();
//        System.out.println(chars.length+"====");
//        Pattern compile1 = Pattern.compile("[0-9]*");
//        for (int i = 0; i < 84; i++) {
//            String title1 = entities.get(i).getTitle();
//            if (title1 == null){
//                continue;
//            }
//
//            System.out.println(entities.get(i).getTitle());
//            Matcher matcher = compile1.matcher(title1);
//            if (matcher.find()){
//                String group = matcher.group(0);
//                if (group.equals("0")){
//                    entities.get(9).setAnswer('A');
//                    continue;
//                }
//                Integer num = Integer.valueOf(group);
//                entities.get(i).setAnswer(chars[num-1]);
//            }
//        }
//        System.out.println(entities);
//        //工具类
//        ExportExcel<Entity> ex = new ExportExcel<Entity>();
//        String[] headers = {"course", "chapter", "title", "a", "b", "c", "d", "answer"};
//        //时间格式化
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
//        //文件名
//        String fileName = "自动导出1.xls";
//        String path = "C:\\Users\\hs";
//        //文件路径
//        path = path + File.separator + fileName;
//        File file1 = new File(path);
//        //输出流
//        FileOutputStream out = new FileOutputStream(file1);
//        //实例化Excel表格
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        //创建工作表单
//        String[] sheetNames = { "计组选择题" };
//        //实例化sheet
//        workbook.createSheet(sheetNames[0]);
//        //导出到Excel
//        ex.exportExcel(sheetNames[0], headers, entities, out,
//                "yyyy-MM-dd HH:mm", workbook);
//        //保存文件
//        workbook.write(out);
//        out.close();
//
//
//
//    }

//    public static void main(String[] args) throws IOException {
//        // 定义即将访问的链接
//        String url = "https://www.cnblogs.com/wc1903036673/p/3484607.html";
//        String result = Utils.getDocByUrl(url);
//        List<Entity> list = getListByResult(result);
//        //设置每道题的答案
//        List<Entity> entities = setAnswer(list);
//        //工具类
//        ExportExcel<Entity> ex = new ExportExcel<Entity>();
//        String[] headers = {"course", "chapter", "title", "a", "b", "c", "d", "answer"};
//        //时间格式化
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
//        //文件名
//        String fileName = "自动导出.xls";
//        String path = "C:\\Users\\hs";
//        //文件路径
//        path = path + File.separator + fileName;
//        File file = new File(path);
//        //输出流
//        FileOutputStream out = new FileOutputStream(file);
//        //实例化Excel表格
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        //创建工作表单
//        String[] sheetNames = { "计组选择题" };
//        //实例化sheet
//        workbook.createSheet(sheetNames[0]);
//        //导出到Excel
//        ex.exportExcel(sheetNames[0], headers, entities, out,
//                "yyyy-MM-dd HH:mm", workbook);
//        //保存文件
//        workbook.write(out);
//        out.close();
//
//
//    }

    /**
     * 将html文档转成list数据
     */
    public static List<Entity> getListByResult(String result) {

        //前置处理
        String s = result.replaceAll("&nbsp", "");
        String s1 = s.replaceAll(";", "");
        //完整题正则
        String pattern = "<p align=\"left\">\\d[\\s\\S]*?(?=<p align=\"left\">\\d)";
        //题目正则
        String pattern1 = "(<p align=\"left\">)(\\d+\\..*)(</p>)";
        //答案正则ABCD
        String pattern2 = "[A-D][\\．\\.][\\s\\S]*?(?=[A-D][\\．\\.]|</p>)";
        Pattern p = Pattern.compile(pattern);
        Pattern p1 = Pattern.compile(pattern1);
        Pattern p2 = Pattern.compile(pattern2);
        Matcher m = p.matcher(s1);
        int count = 0;
        //存放结果的list
        List<Entity> list = new ArrayList<Entity>();
        while (m.find()) {
            Entity entity = new Entity();
            System.out.println("----------------------------------------------------");
            //题目
            Matcher matcher = p1.matcher(m.group(0));
            if (matcher.find()) {
                System.out.println(matcher.group(2));
                entity.setTitle(matcher.group(2));
            }
            //答案ABCD
            Matcher matcher1 = p2.matcher(m.group(0));
            for (int i = 1; i < 5; i++) {
                if (matcher1.find()) {
                    if (i == 1) {
                        entity.setA(matcher1.group(0));
                    }
                    if (i == 2) {
                        entity.setB(matcher1.group(0));
                    }
                    if (i == 3) {
                        entity.setC(matcher1.group(0));
                    }
                    if (i == 4) {
                        entity.setD(matcher1.group(0));
                    }
                }
            }
            //章节
            entity.setChapter("1");
            //课程
            entity.setCourse("1");
            System.out.println("----------------------------------------------------");
            list.add(entity);
            count++;
        }
        Iterator<Entity> iterator = list.iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            if (entity.getTitle() == null || entity.getA() == null || entity.getB() == null) {
                iterator.remove();
            }
        }
        System.out.println(list.size());
        System.out.println(count);
        return list;
    }

    /**
     * 设置每道题目的答案
     */
    public static List<Entity> setAnswer(List list) {
        String answer = "1-5 DBADD   6-10 DACAB    11-15 ACDCB   16-20 ABDCB  21-25 CCBAD\n" +
                "\n" +
                "26-30 CBBBD   31-35 ABCBA   36-40 ABCCC   41-45 DCCBD   46-50 CCBBC\n" +
                "\n" +
                "51-55 DCCAA   56-60 DBACB   61-65 CCACB   66-70 BCADD      71-75 CBBAB\n" +
                "\n" +
                "76-80 AAACD   81-85 DAACB   86-90 BCBCC   91-95 CBCAA     96-100 BDCBB\n" +
                "\n" +
                "101-105 AACDB   106-110 BDAAA  111-115 CBBBA  116-120 CABCC\n" +
                "\n" +
                "121-125 DBCDC   126-130 CACDA  131-135 ADADA  136-140 CACAC\n" +
                "\n" +
                "141-145 ABDDD   146-150 DCBBA  151-155 CBCAA  156-160 CDDCA\n" +
                "\n" +
                "161-165 BADAB   166-170 DCDAD  171-175 BBCAD";
        String s1 = answer.replaceAll(" ", "");
        String s2 = s1.replaceAll("\n", "");
        System.out.println(s2);

        Pattern compile = Pattern.compile("[0-9]*-[0-9]*");
        Matcher m = compile.matcher(s2);
        String s = m.replaceAll("");
        System.out.println(s);
        char[] chars = s.toCharArray();
        Pattern compile1 = Pattern.compile("^[0-9]*");
        for (int i = 0; i < 143; i++) {
            Entity entity = (Entity) list.get(i);
            String title = entity.getTitle();
            Matcher m1 = compile1.matcher(title);
            String num = "";
            if (m1.find()) {
                num = m1.group(0);
            }
            Integer num1 = Integer.valueOf(num);
            entity.setAnswer(chars[num1 - 1]);

        }
        return list;
    }

    public static String getAnswer() {
        String s = "1. D\n" +
                "2. B\n" +
                "3. C\n" +
                "4. C\n" +
                "5. A\n" +
                "6. A\n" +
                "7. D\n" +
                "8. A\n" +
                "9. C\n" +
                "10. A\n" +
                "11. C\n" +
                "12. D\n" +
                "13. C\n" +
                "14. B\n" +
                "15. C\n" +
                "16. C\n" +
                "17. D\n" +
                "18. B\n" +
                "19. B\n" +
                "20. B\n" +
                "21. D\n" +
                "22. A\n" +
                "23. A\n" +
                "24. C\n" +
                "25. D\n" +
                "26. C\n" +
                "27. A\n" +
                "28. A\n" +
                "29. C\n" +
                "30. B\n" +
                "31. C\n" +
                "32. C\n" +
                "33. B\n" +
                "34. B\n" +
                "35. D\n" +
                "36. C\n" +
                "37. C\n" +
                "38. C\n" +
                "39. A\n" +
                "40. C\n" +
                "41. D\n" +
                "42. A\n" +
                "43. A\n" +
                "44. B\n" +
                "45. C\n" +
                "46. A\n" +
                "47. B\n" +
                "48. B\n" +
                "49. A\n" +
                "50. B\n" +
                "51. B\n" +
                "52. B\n" +
                "53. C\n" +
                "54. C\n" +
                "55. A\n" +
                "56. C\n" +
                "57. D\n" +
                "58. B\n" +
                "59. C\n" +
                "60. D\n" +
                "61. B\n" +
                "62. B\n" +
                "63. A\n" +
                "64. C\n" +
                "65. D\n" +
                "66. B\n" +
                "67. D\n" +
                "68. D\n" +
                "69. A\n" +
                "70. B\n" +
                "71. B\n" +
                "72. D\n" +
                "73. B\n" +
                "74. A\n" +
                "75. C\n" +
                "76. B\n" +
                "77. D\n" +
                "78. B\n" +
                "79. A\n" +
                "80. A\n" +
                "81. C\n" +
                "82. A\n" +
                "83. A\n" +
                "84. D\n" +
                "85. C";
        String s1 = s.replaceAll("[0-9]|\\.|\\s|\\n", "");
        return s1;
    }
}

