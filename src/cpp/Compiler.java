package cpp;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Compiler extends Frame implements ActionListener{
        int row = 1;//数排列,横坐标
        int line = 1;//横排行，竖坐标。当遇到回车换行的时候，line++,row = 0;

        MenuBar mb = new MenuBar();// 菜单栏
/*public static final AccessibleRole MENU通常出现在菜单栏中的对象，它包含一个用户可以从中选择的操作列表。
*菜单可以带有作为其子级的任何对象，但通常这些对象是菜单项、其他菜单或诸如单选按钮、复选框或分隔符这样的基本对象。
*例如，应用程序可能有 "Edit" 菜单，它包含 "Cut" 和 "Paste" 菜单项。
*/      Menu fileMenu = new Menu("File");//通常出现在菜单栏中的对象，它包含一个用户可以从中选择的操作列表。
        Menu actionMenu = new Menu("Project");
        MenuItem closeWindow = new MenuItem("Exit");//构造具有指定的标签没有键盘快捷方式的的新菜单项。
        MenuItem openFile = new MenuItem("Open file");//构造具有指定的标签没有键盘快捷方式的的新菜单项。
        MenuItem lexical_check = new MenuItem("Check lex");//构造具有指定的标签没有键盘快捷方式的的新菜单项。

        int begin = 0;//当读到第一个字母的时候，标志其位置
        int end = 0;
        TextArea text = new TextArea(25,60);            //初始化textarea文本框 text
        TextArea error_text = new TextArea(10,60);      //初始化textarea文本框 error_text
        //FileDialog file_dialog_load;                 创建一个具有指定标题的文件对话框窗口，用于加载或保存文件。
        FileDialog file_dialog_load = new FileDialog(this, "Open file...", FileDialog.LOAD);

        Compiler(){
                //file_dialog_load.setVisible(true);
                /*
                 *定义布局类型(Panel和Applet的缺省布局管理器)   利用构超函数的特性  首先自调用
                 */
                this.setLayout(new FlowLayout());

                /*
                 *设置窗体类型 以及
                 */
                this.setMenuBar(mb);
                mb.add(fileMenu);
                mb.add(actionMenu);

                fileMenu.add(openFile);
                fileMenu.add(closeWindow);

                actionMenu.add(lexical_check);

                this.add(text);
                this.add(error_text);
                error_text.setText("Lexical Information: \n");

                closeWindow.addActionListener(this);
                openFile.addActionListener(this);
                lexical_check.addActionListener(this);
                this.setSize(500, 600);

                this.addWindowListener(new WindowAdapter(){
                        public void windowClosing(WindowEvent e){
                                System.exit(0);
                        }
                });
                this.setVisible(true);
        }
        /**
         * @param args
         */
        public static void main(String[] args) {
                // TODO Auto-generated method stub
                Compiler compiler = new Compiler();
        }

        //定义actionPerformed事件处理方法
        public void actionPerformed(ActionEvent e){
                if(e.getSource() == closeWindow){
                        System.exit(0);
                }else if(e.getSource() == openFile){
                        file_dialog_load.setVisible(true);
                        //获得文件的路径和文件名
                        File myfile = new File(file_dialog_load.getDirectory(), file_dialog_load.getFile());
                        try{    /*从字符输入流中读取文本，缓冲各个字符，从而提供字符、数组和行的高效读取。
                                  *将缓冲bufReader指定文件的输入*/
                                BufferedReader bufReader = new BufferedReader(new FileReader(myfile));

                                String content = "";
                                String str;

                                /*
                                 *readLine()  读取一个文本行。
                                 *判断文件是否为空
                                */
                                while((str = bufReader.readLine()) != null){
                                        content += str + "\n";
                                        text.setText(content);
                                }
                        }catch(IOException ie){
                                System.out.println("IOexception occurs...");
                        }
                }else if(e.getSource() == lexical_check){
                        //error_text.setText("Lexical Information: \n");
                        error_text.setText("");
                        row = 0;
                        line = 1;
                        checkLexical();
                }
        }
        /*
         * 对Text区域数据区进行词法检测
         * */
        public void checkLexical(){
                String error_info = error_text.getText();
                String content = text.getText();
                if(content.equals("")){
                        error_info += "Text is empty! You havn't input any code!\n";
                        error_text.setText(error_info);
                }
                else{
                        int i = 0;//选择第i个字符进行检测。
                        int N = content.length();//文件大小

                        //假如是空格开头的文件会出现  java.lang.StringIndexOutOfBoundsException 异常。非空格开头的即可文件正常
                        //error_text.appendText("N = " + N + '\n');

                        int state = 0;//状态标志
                        for(i = 0; i < N; i++){//对所有字符进行检测
                                row++;

                                //charAt(i)  读取i相对于当前位置的给定索引处的字符
                                char c = content.charAt(i);
                                //error_text.appendText("" + c);
                                //System.out.print(content);
                                switch(state){
                                        case 0:
                                                //row++;
                                                if(c == ',' || c == ' ' || c == '\t' || c == '{' || c =='}' || c == '(' || c == ')' || c == ';' || c == '[' || c == ']') {
                                                        if(isDigit(content.charAt(i - 1)) && isDigit(content.charAt(begin))){
                                                                end = i;
                                                                error_text.append("info: 数值表达式 " + content.substring(begin, end) + '\n');
                                                        }

                                                        //error_text.appendText("info: 分隔符 " + c + '\n');
                                                        state = 0;
                                                }
                                                else if(c == '+') state = 1;
                                                else if(c == '-') state = 2;
                                                else if(c == '*') state = 3;
                                                else if(c == '/') state = 4;
                                                else if(c == '!') state = 5;
                                                else if(c == '>') state = 6;
                                                else if(c == '<') state = 7;
                                                else if(c == '=') state = 8;
                                                else if(((int)c) == 10) state = 9;//输入为回车
                                                //else if(c == '\n') state = 9;//输入为回车

                                                //isLetter()是接口 java.sql.ResultSet 中的方法   功能：检索指针是否位于此 ResultSet 对象的最后一行。
                                                else if(isLetter(c)) {
                                                        state = 10;
                                                        begin = i;
                                                }
                                               //isDigit(int) - 类 java.lang.Character 中的静态方法  功能：确定指定字符（Unicode 代码点）是否为数字。
                                                else if(isDigit(c)) {
                                                        begin = i;
                                                        state = 11;
                                                }
                                                else if(c == '#') state = 12;
                                                else if(c == '&') state = 14;
                                                else if(c == '|') state = 15;
                                                else if(c == '"') state = 16;
                                                else error_text.appendText("line: " + line + " row: " + row + " error: '" + c + "' Undefined character! \n");
                                                break;
                                        case 1://标志符是 +
                                                //row++;
                                                if(c == '+'){
                                                        state = 0;
                                                        error_text.appendText("info: 运算符 '++'\n");
                                                }
                                                else if(c == '='){
                                                        state = 0;
                                                        error_text.appendText("info: 运算符 '+='\n");

                                                }else{
                                                        state = 0;
                                                        error_text.appendText("info: 运算符 '+'\n");
                                                        i--;
                                                        row--;
                                                }
                                                break;
                                        case 2://标志符是 -
                                                if(c == '-')
                                                        error_text.appendText("info: 运算符 '--'\n");
                                                else if(c == '=')
                                                        error_text.appendText("info: 运算符 '-='\n");
                                                else{
                                                        error_text.appendText("info: 运算符 '-'\n");
                                                        i--;
                                                        row--;
                                                }
                                                state = 0;
                                                break;
                                        case 3://标志符是 *
                                                if(c == '=')
                                                        error_text.appendText("info: 运算符 '*='\n");
                                                else{
                                                        error_text.appendText("info: 运算符 '*'\n");
                                                        i--;
                                                        row--;
                                                }
                                                state = 0;
                                                break;
                                        case 4://标志符是 /
                                                if(c == '/'){
                                                        while((c) != '\n'){
                                                                c = content.charAt(i);
                                                                i++;
                                                        }
                                                        state = 0;
                                                        error_text.appendText("info: 注释部分 // \n");
                                                }else if(c == '='){
                                                        state = 0;
                                                        error_text.appendText("info: 运算符 /= \n");
                                                }else{
                                                        state = 0;
                                                        error_text.appendText("info: 运算符 / \n");
                                                        i--;
                                                        row--;
                                                }
                                                //state = 0;
                                                break;
                                        case 5://标志符是 !
                                                if(c == '='){
                                                        error_text.appendText("info: 运算符 != \n");
                                                        state = 0;
                                                }else{
                                                        state = 0;
                                                        i--;
                                                        row--;
                                                        error_text.appendText("info: 运算符 ! \n");
                                                }
                                                //state = 0;
                                                break;
                                        case 6://标志符是 >
                                                if(c == '='){
                                                        error_text.appendText("info: 运算符 >= \n");
                                                        state = 0;
                                                }else{
                                                        state = 0;
                                                        error_text.appendText("info: 元算符 > \n");
                                                }
                                                //state = 0;
                                                break;
                                        case 7://标志符是 <
                                                if(c == '='){
                                                        error_text.appendText("info: 运算符 <= \n");
                                                        state = 0;
                                                }else{
                                                        state = 0;
                                                        error_text.appendText("info: 运算符 < \n");
                                                }
                                                break;
                                        case 8://标志符是 =
                                                if(c == '='){
                                                        error_text.appendText("info: 运算符 == \n");
                                                        state = 0;
                                                }else{
                                                        state = 0;
                                                        error_text.appendText("info: 运算符 = \n");
                                                }
                                                break;
                                        case 9://标志符是 回车
                                                state = 0;
                                                row = 1;
                                                line ++;
                                                break;
                                        case 10://标志符是 字母
                                                if(isLetter(c) || isDigit(c)){
                                                        state = 10;
                                                }else{
                                                        end = i;
                                                        String id = content.substring(begin, end);
                                                        if(isKey(id))
                                                                error_text.appendText("info: 关键字 " + id + '\n');
                                                        else
                                                                error_text.appendText("info: 标志符 " + id + '\n');
                                                        i--;
                                                        row--;
                                                        state = 0;
                                                }
                                                //state = 0;
                                                break;
                                        case 11://标志符是 数字
                                                if(c == 'e' || c == 'E')
                                                        state = 13;
                                                else if(isDigit(c) || c == '.'){
                                                        //省略跳过，i加一操作
                                                }else {
                                                        if(isLetter(c)){
                                                                error_text.appendText("error: line " + line + " row " + row + " 数字格式错误或者标志符错误\n");
                                                        }
                                                        //i--;
                                                        //row--;
                                                        int temp = i;
                                                        i = find(i,content);
                                                        row += (i - temp);
                                                        state = 0;
                                                }

                                                break;
                                        case 12://标志符是 #
                                                String id = "";
                                                while(c != '<'){
                                                        id += c;
                                                        i++;
                                                        c = content.charAt(i);
                                                }
                                                if(id.trim().equals("include")){
                                                        while(c != '>' && ( c != '\n')){
                                                                i++;
                                                                c = content.charAt(i);
                                                        }
                                                        if(c == '>')
                                                                error_text.append("info: 头文件引入文法 \n");
                                                }else
                                                        error_text.appendText("error: " + "line " + line + ", row " + row + " 语法错误\n");
                                                //i--;
                                                //row--;

                                                state = 0;
                                                break;
                                        case 13://检测指数表示方式
                                                if(c == '+' || c == '-' || isDigit(c)){
                                                        i++;
                                                        c = content.charAt(i);
                                                        while(isDigit(c)){
                                                                i++;
                                                                c = content.charAt(i);
                                                        }
                                                        if(isLetter(c) || c == '.'){
                                                                error_text.appendText("error； line " + line + " row " + row + " 指数格式错误！\n");
                                                                state = 0;
                                                                //i--;
                                                                //row--;
                                                                int temp = i;
                                                                i = find(i,content);
                                                                row += (i - temp);
                                                                //error_text.appendText("i = " + i + " row = " + row + " len = " + content.length() + '\n');
                                                        }else{
                                                                end = i;
                                                                error_text.appendText("info: 指数 " + content.substring(begin, end) + '\n');
                                                        }
                                                        /*
                                                        end = i;
                                                        String str = content.substring(begin, end);
                                                        error_text.appendText("info: 指数 " + str + '\n');
                                                        */
                                                        state = 0;
                                                }
                                                break;
                                        case 14:// 逻辑运算发 &&
                                                if(c == '&')
                                                        error_text.appendText("info: 逻辑AND运算符 '&&' \n");
                                                else{
                                                        i--;
                                                        error_text.appendText("info: & 不明运算符. \n");
                                                }
                                                state = 0;
                                                break;
                                        case 15:// 逻辑运算发 ||
                                                if(c == '|')
                                                        error_text.appendText("info: 逻辑OR运算符 '||' \n");
                                                else{
                                                        i--;
                                                        error_text.appendText("info: '|' 不明运算符. \n");
                                                }
                                                state = 0;
                                                break;
                                        case 16:
                                                error_text.appendText("info: 引号 " + '"' + '\n');
                                                i--;
                                                state = 0;
                                                break;

                                }
                        }
                }
                error_text.appendText("Have checked lexical! \n");


        }


        boolean isLetter(char c){
                if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_')
                        return true;
                return false;
        }


        boolean isDigit(char c){
                if(c >= '0' && c <= '9') return true;
                return false;
        }


        boolean isKey(String str){
                if(str.equals("int") || str.equals("real") || str.equals("for") || str.equals("while") || str.equals("do") || str.equals("void") || str.equals("float") || str.equals("if") || str.equals("then")||
                                str.equals("AND") || str.equals("OR") || str.equals("NOT") || str.equals("repeat") || str.equals("until") || str.equals("double") || str.equals("write") || str.equals("return") || str.equals("true")
                                || str.equals("false") || str.equals("boolean") || str.equals("program") || str.equals("const") || str.equals("to"))
                        return true;
                return false;
        }


        int find(int begin, String str){//寻找分隔符空格、括号、回车等。
                if(begin >= str.length())
                        return str.length();
                for(int i = begin; i < str.length(); i++){
                        char c = str.charAt(i);
                        if(c == '\n' || c == ',' || c == ' ' || c == '\t' || c == '{' || c =='}' || c == '(' || c == ')' || c == ';' || c == '=' || c == '+'|| c == '-' || c == '*' || c == '/')
                                return i - 1;
                }
                return str.length();
        }

}
