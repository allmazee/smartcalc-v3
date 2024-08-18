#include "../Models/credit_model.h"

namespace s21 {
class CreditController {
 public:
  CreditController(){};
  ~CreditController(){};

  void set_data(CreditModel::CreditType type, double amount, int term,
                int rate);
  std::vector<double> get_monthly_payment() const;
  double get_overpayment() const;
  double get_total_payment() const;

  void Calculate();

 private:
  CreditModel model_;
};
}  // namespace s21
