#include "credit_controller.h"

namespace s21 {
void CreditController::set_data(CreditModel::CreditType type, double amount,
                                int term, int rate) {
  model_.set_credit_type(type);
  model_.set_amount(amount);
  model_.set_term(term);
  model_.set_rate(rate);
}

std::vector<double> CreditController::get_monthly_payment() const {
  return model_.get_monthly_payment();
}

double CreditController::get_overpayment() const {
  return model_.get_overpayment();
}

double CreditController::get_total_payment() const {
  return model_.get_total_payment();
}

void CreditController::Calculate() { model_.Calculate(); }
}  // namespace s21
