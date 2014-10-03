/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Kenneth Ham
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.epictodo.Controller.JSON;

public class TokenTypes {
	public static final int COLON = 1;
	public static final int LEFT_BRACE = 2;  // {
	public static final int RIGHT_BRACE = 3;  // }
	public static final int LEFT_BRACKET = 4; // [
	public static final int RIGHT_BRACKET = 5; // ]
	public static final int COMMA = 6;
	public static final int INTEGER = 7;
	public static final int NUMBER = 8;
	public static final int STRING = 9;
	public static final int NULL = 10;
	public static final int TRUE = 11;
	public static final int FALSE = 12;
	public static final int EOF = 999;
	public static final String MSG_UNKNOWN_TOKENTYPE = "UNKNOWN TOKEN TYPE";

	public static final String asString(int token_type) {
		switch (token_type) {
			case COLON :
				return "COLON";
			case LEFT_BRACE :
				return "LEFT_BRACE";
			case RIGHT_BRACE :
				return "RIGHT_BRACE";
			case LEFT_BRACKET :
				return "LEFT_BRACKET";
			case RIGHT_BRACKET :
				return "RIGHT_BRACKET";
			case COMMA :
				return "COMMA";
			case INTEGER :
				return "INTEGER";
			case NUMBER :
				return "NUMBER";
			case STRING :
				return "STRING";
			case NULL :
				return "NULL";
			case TRUE :
				return "TRUE";
			case FALSE :
				return "FALSE";
			case EOF :
				return "EOF";
			default :
				throw new Error(MSG_UNKNOWN_TOKENTYPE);
		}
	}
}
