#include "edu_school21_smartcalc_model_CalculatorJNI.h" 
#include "Controllers/calculator_controller.h"

s21::CalculatorController controller;

extern "C" {

JNIEXPORT void JNICALL Java_edu_school21_smartcalc_model_CalculatorJNI_calculate(JNIEnv* env, jobject obj, jstring infix, jdouble x) {
    const char* infixChars = env->GetStringUTFChars(infix, NULL);
    std::string infixStr(infixChars);
    controller.Calculate(infixStr, x);
    env->ReleaseStringUTFChars(infix, infixChars);
}

JNIEXPORT jdouble JNICALL Java_edu_school21_smartcalc_model_CalculatorJNI_getResult(JNIEnv* env, jobject obj) {
    return controller.get_result();
}

JNIEXPORT jint JNICALL Java_edu_school21_smartcalc_model_CalculatorJNI_getStatus(JNIEnv* env, jobject obj) {
    return static_cast<jint>(controller.get_status());
}

}
