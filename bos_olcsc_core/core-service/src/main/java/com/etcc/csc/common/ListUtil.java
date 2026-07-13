package com.etcc.csc.common;

import java.util.ArrayList;

import com.etcc.csc.dto.SecurityQuestionDTO;

public class ListUtil {

	public static boolean checkForDuplicateSecuirtyQuestion(
			ArrayList<SecurityQuestionDTO> list) {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		boolean contains = false;
		for (int i = 0; i < list.size(); i++) {
			if (!temp.contains(new Integer(list.get(i).getQuestionId()))) {
				temp.add(new Integer(list.get(i).getQuestionId()));
			} else {
				contains = true;
				break;
			}
		}
		return contains;
	}
}
