package com.dats.dto;

import java.util.ArrayList;

public class Inword_Converter {
	String[] numword = {"One","Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Eleven","Twelve","Thirteen","Fourteen","Fifteen","Sixteen","Seventeen","Eighteen","Nineteen","Twenty"};
	String[] S= {"Ten","Twenty","Thirty","Forty","Fifty","Sixty","Seventy","Eighty","Ninety"}; 
	ArrayList<String> list = new ArrayList<>();

	public String converter(int n) {
		String sum = "";
		
		while(n>0) {
		if(n>20&&n<100) {
			int ca = n/10;
			n=n%10;
			sum+= S[ca-1];
		}
		else if(n<=20) {
			sum+=numword[n-1];
			n=0;
		}
		else if (n>99&&n<1000) {
			int ca = n/100;
			n=n%100;
			sum+= numword[ca-1]+" Hundred ";
		}
		else if(n>999) {
			int ca = n/1000;
			n=n%1000;
			if(ca>19) {
				int de = ca%10;
				int carry=ca/10;
				sum+=S[carry-1];
				if(de>0) {
					sum+=numword[de-1];
				}
				sum+=" Thousand ";
			}
			else
			sum+= numword[ca-1]+" Thousand ";
		}
		}
		return sum+" Taka Only";
	}
}
