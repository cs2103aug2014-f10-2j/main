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

package com.epictodo.controller.nlp;

import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.*;

import java.util.LinkedList;
import java.util.List;

public class GrammaticalParser {
    protected LexicalizedParser lexicalized_parser = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
    protected TreebankLanguagePack treebank_languagepack = new PennTreebankLanguagePack();
    protected GrammaticalStructureFactory grammarical_structure_factory = treebank_languagepack.grammaticalStructureFactory();
    protected GrammaticalStructure grammartical_structure;
    protected List<TypedDependency> typed_dependency_list;

    public GrammaticalParser() {
        lexicalized_parser.setOptionFlags(new String[]{"-maxLength", "80", "-retainTmpSubcategories"});
    }

    public Tree buildGrammarStructure(String _sentence) {
        Tree _tree = lexicalized_parser.parse(_sentence);

        return _tree;
    }

    public List<TypedDependency> grammarAnalyzer(String _sentence) {
        Tree _tree = lexicalized_parser.parse(_sentence);
        grammartical_structure = grammarical_structure_factory.newGrammaticalStructure(_tree);
        typed_dependency_list = grammartical_structure.typedDependenciesCCprocessed();

        return typed_dependency_list;
    }

    public List<String> grammartize(Tree _tree, String _sentence) {
        List<String> _grammar = new LinkedList<String>();
        _tree = lexicalized_parser.parse(_sentence);
        grammartical_structure = grammarical_structure_factory.newGrammaticalStructure(_tree);
        typed_dependency_list = grammartical_structure.typedDependenciesCCprocessed();

        for(int i = 0; i < typed_dependency_list.size(); i++) {
            _grammar.add(typed_dependency_list.get(i).dep().nodeString());
        }

        return _grammar;
    }
}
