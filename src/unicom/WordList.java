package unicom;
/**
 * 关键字表，一共29个
 * @author lenovo
 *
 */
public class WordList {
	//此类定义了语言单词符号种别码
	 WordUnit[] w = new WordUnit[30];
	 public WordList(){
		 //关键字
		         w[0] = new WordUnit();w[0].setID(1);w[0].setValue("class");
		 		 w[1] = new WordUnit();w[1].setID(2);w[1].setValue("int");
		 		 w[2] = new WordUnit();w[2].setID(3);w[2].setValue("char");
		 		 w[3] = new WordUnit();w[3].setID(4);w[3].setValue("if");
		 		 //w[4] = new word();w[4].setID(5);w[4].setValue("else");
		 		 w[4] = new WordUnit();w[4].setID(5);w[4].setValue("for");
		 		 w[5] = new WordUnit();w[5].setID(6);w[5].setValue("while");
		 		 w[6] = new WordUnit();w[6].setID(7);w[6].setValue("ID");
		 		 w[7] = new WordUnit();w[7].setID(8);w[7].setValue("NUM");
		 		 w[8] = new WordUnit();w[8].setID(9);w[8].setValue("=");
		 		 w[9] = new WordUnit();w[9].setID(10);w[9].setValue("+");
		 		 w[10] = new WordUnit();w[10].setID(11);w[10].setValue("-");
		 		 w[11] = new WordUnit();w[11].setID(12);w[11].setValue("*");
		 		 w[12] = new WordUnit();w[12].setID(13);w[12].setValue("/");
		 		 w[13] = new WordUnit();w[13].setID(14);w[13].setValue("(");
		 		 w[14] = new WordUnit();w[14].setID(15);w[14].setValue(")");
		 		 w[15] = new WordUnit();w[15].setID(16);w[15].setValue("[");
		 		 w[16] = new WordUnit();w[16].setID(17);w[16].setValue("]");
		 		 w[17] = new WordUnit();w[17].setID(18);w[17].setValue("{");
		 		 w[18] = new WordUnit();w[18].setID(19);w[18].setValue("}");
		 		 w[19] = new WordUnit();w[19].setID(20);w[19].setValue(",");
		 		 w[20] = new WordUnit();w[20].setID(21);w[20].setValue(":");
		 		 w[21] = new WordUnit();w[21].setID(22);w[21].setValue(";");
		 		 w[22] = new WordUnit();w[22].setID(23);w[22].setValue(">");
		 		 w[23] = new WordUnit();w[23].setID(24);w[23].setValue("<");
		 		 w[24] = new WordUnit();w[24].setID(25);w[24].setValue(">=");
		 		 w[25] = new WordUnit();w[25].setID(26);w[25].setValue("<=");
		 		 w[26] = new WordUnit();w[26].setID(27);w[26].setValue("==");
		 		 w[27] = new WordUnit();w[27].setID(28);w[27].setValue("!=");
		 		 w[28] = new WordUnit();w[28].setID(29);w[28].setValue("ERROR");
	
	}
	public int Reserve(String value){
		for(int i = 0 ; i<28 ; i++){
			if(value.equals(w[i].getValue())){
				return w[i].getID();
			}
		}
		return 0;   //返回0表示不在保留字之中。
	}
}