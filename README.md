EditTextAutoAddSymbol
=====================

EditText回车自动添加符号,一个单元一个单元的删除。


EditText mEditText = findViewById(R.id.et);

EditTextAutoAddSymbol editTextAutoAddSymbol = new EditTextAutoAddSymbol(this, mEditText, ";", 5);

mEditText.addTextChangedListener(editTextAutoAddSymbol);

mEditText.setOnEditorActionListener(editTextAutoAddSymbol);

// 例如： 哈哈;嘻嘻;乐乐;
