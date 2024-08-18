#include "calculator_controller.h"

#include <string>

namespace s21 {
void CalculatorController::Calculate(const std::string &infix, const double x) {
  model_.set_infix(infix);
  model_.set_x(x);
  model_.Calculate();
}

CalculatorModel::Status CalculatorController::get_status() const {
  return model_.get_status();
}

double CalculatorController::get_result() const { return model_.get_result(); }
}  // namespace s21
