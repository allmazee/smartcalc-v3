#include "edu_school21_smartcalc_model_CreditModel.h"
#include "Models/credit_model.h"
#include <vector>

s21::CreditModel model;

JNIEXPORT void JNICALL
Java_edu_school21_smartcalc_model_CreditModel_nativeSetCreditType(
    JNIEnv *env, jobject obj, jint creditType) {
  model.set_credit_type(static_cast<s21::CreditModel::CreditType>(creditType));
}

JNIEXPORT void JNICALL
Java_edu_school21_smartcalc_model_CreditModel_nativeSetCreditAmount(
    JNIEnv *env, jobject obj, jdouble creditAmount) {
  model.set_amount(creditAmount);
}

JNIEXPORT void JNICALL
Java_edu_school21_smartcalc_model_CreditModel_nativeSetCreditTerm(
    JNIEnv *env, jobject obj, jint creditTerm) {
  model.set_term(creditTerm);
}

JNIEXPORT void JNICALL
Java_edu_school21_smartcalc_model_CreditModel_nativeSetCreditRate(
    JNIEnv *env, jobject obj, jint creditRate) {
  model.set_rate(creditRate);
}

JNIEXPORT void JNICALL
Java_edu_school21_smartcalc_model_CreditModel_nativeCalculate(JNIEnv *env,
                                                              jobject obj) {
  model.Calculate();
}

JNIEXPORT jdouble JNICALL
Java_edu_school21_smartcalc_model_CreditModel_nativeGetOverpayment(
    JNIEnv *env, jobject obj) {
  return model.get_overpayment();
}

JNIEXPORT jdouble JNICALL
Java_edu_school21_smartcalc_model_CreditModel_nativeGetTotalPayment(
    JNIEnv *env, jobject obj) {
  return model.get_total_payment();
}

JNIEXPORT jobjectArray JNICALL
Java_edu_school21_smartcalc_model_CreditModel_nativeGetMonthlyPayments(
    JNIEnv *env, jobject obj) {
  std::vector<double> monthly_payments = model.get_monthly_payment();
  jclass doubleClass = env->FindClass("java/lang/Double");
  jobjectArray result =
      env->NewObjectArray(monthly_payments.size(), doubleClass, nullptr);

  jmethodID doubleConstructor = env->GetMethodID(doubleClass, "<init>", "(D)V");

  for (size_t i = 0; i < monthly_payments.size(); i++) {
    jobject doubleObj =
        env->NewObject(doubleClass, doubleConstructor, monthly_payments[i]);
    env->SetObjectArrayElement(result, i, doubleObj);
  }

  return result;
}
