package com.compiler;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor7;

/**
 * 生成一个访问器（微信登录）
 */

public class EntryVisitor extends SimpleAnnotationValueVisitor7<Void,Void> {

    private final Filer FILER; //需要遍历的东西

    private String mPackageName =null; //最终获取的包名

    public EntryVisitor(Filer FILER) {
        this.FILER = FILER;
    }

    @Override
    public Void visitString(String s, Void p) {

        mPackageName =s;

        return p;
    }

    @Override
    public Void visitType(TypeMirror t, Void p) {

        generateJavaCode(t);
        return p;
    }


    //生成模板代码
    private void generateJavaCode(TypeMirror typeMirror){
        final TypeSpec targetActivity = TypeSpec.classBuilder("WXEntryActivity") //微信规定的类名
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.FINAL)
                .superclass(TypeName.get(typeMirror))
                .build();

        //微信规定的包名： app包名+.wxapi
        final JavaFile javaFile = JavaFile.builder(mPackageName+".wxapi",targetActivity)
                .addFileComment("微信入口文件") //注释
                .build();
        try{
            javaFile.writeTo(FILER);
        }catch (IOException e){
            e.printStackTrace();
        }

    }



}
