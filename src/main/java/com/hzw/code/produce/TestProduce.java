package com.hzw.code.produce;

public class TestProduce {
	public static void main(String[] args) {
		try {
			CodeProduce produce = new CodeProduce("im_group","group","com.xuanwu.im","xiangshaoxu");
//			CodeProduce produce = new CodeProduce("im_user","user","com.xuanwu.im","xiangshaoxu");
//			CodeProduce produce = new CodeProduce("group_user_map","groupUserMap","com.xuanwu.im","xiangshaoxu");
			produce.doProduce();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
