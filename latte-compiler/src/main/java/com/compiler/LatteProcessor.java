package com.compiler;

import com.compiler.annotations.AppRegisterGenerator;
import com.compiler.annotations.EntryGenerator;
import com.compiler.annotations.PayEntryGenerator;
import com.google.auto.service.AutoService;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

/**
 *  仿ButterKnife注解框架，编译期生成代码
 *
 */

@SuppressWarnings("unused")
@AutoService(Processor.class) //生成相关代码，这类代码是不可见的。编译期间完成
public final class LatteProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {

        final Set<String> types = new LinkedHashSet<>();
        final Set<Class<? extends Annotation>> supportAnnotations =getSupportedAnnotations();
        for (Class<? extends Annotation> annotation :supportAnnotations){
            types.add(annotation.getCanonicalName());
        }
        return types;
    }


    //传入需要的 注解类
    private Set<Class<? extends Annotation>> getSupportedAnnotations(){

        final Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(EntryGenerator.class);
        annotations.add(PayEntryGenerator.class);
        annotations.add(AppRegisterGenerator.class);

        return annotations;
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {

        generateEntryCode(env);
        generateAppRegisterCode(env);
        generateAppRegisterCode(env);
        return true;
    }

    private void scan(RoundEnvironment env,
                      Class<? extends Annotation> annotation,
                      AnnotationValueVisitor visitor) {

        for (Element typeElement : env.getElementsAnnotatedWith(annotation)) {
            final List<? extends AnnotationMirror> annotationMirrors =
                    typeElement.getAnnotationMirrors();

            for (AnnotationMirror annotationMirror : annotationMirrors) {
                final Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues
                        = annotationMirror.getElementValues();

                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry
                        : elementValues.entrySet()) {
                    entry.getValue().accept(visitor, null);
                }
            }
        }
    }

   //生成微信登录
    private void generateEntryCode(RoundEnvironment env){
        final EntryVisitor entryVisitor = new EntryVisitor(processingEnv.getFiler());
        scan(env,EntryGenerator.class,entryVisitor);
    }


    //生成微信支付
    private void generatePayEntryCode(RoundEnvironment env){

        final PayEntryVisitor payEntryVisitor = new PayEntryVisitor(processingEnv.getFiler());
        scan(env,PayEntryGenerator.class,payEntryVisitor);

    }


    //生成微信广播
    private void generateAppRegisterCode(RoundEnvironment env) {
        final AppRegisterVisitor appRegisterVisitor =
                new AppRegisterVisitor(processingEnv.getFiler());
        scan(env, AppRegisterGenerator.class, appRegisterVisitor);
    }

}
