#ifndef CREDIT_MODEL_H
#define CREDIT_MODEL_H

#include <string>
#include <vector>

namespace s21 {
class CreditModel {
 public:
  CreditModel(){};
  ~CreditModel(){};

  enum class CreditType { DIFF, ANNUITY };

  CreditType get_credit_type() const;
  double get_amount() const;
  int get_term() const;
  int get_rate() const;

  std::vector<double> get_monthly_payment() const;
  double get_overpayment() const;
  double get_total_payment() const;

  void set_credit_type(CreditType type);
  void set_amount(double amount);
  void set_term(int term);
  void set_rate(int rate);

  void Calculate();

 private:
  double credit_amount_;
  int credit_term_;
  double credit_rate_;
  CreditType credit_type_;
  std::vector<double> monthly_payment_;
  double overpayment_;
  double total_payment_;

  void AnnuityPayment();
  void DifferentiatedPayment();
};
}  // namespace s21

#endif