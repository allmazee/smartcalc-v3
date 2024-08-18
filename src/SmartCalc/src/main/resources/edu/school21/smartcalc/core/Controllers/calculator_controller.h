#ifndef CALCULATOR_CONTROLLER_H
#define CALCULATOR_CONTROLLER_H

#include "../Models/calculator_model.h"

namespace s21 {
class CalculatorController {
 public:
  CalculatorController(){};
  ~CalculatorController(){};

  void Calculate(const std::string &infix, const double x);
  CalculatorModel::Status get_status() const;
  double get_result() const;

 private:
  CalculatorModel model_;

};  // CalculatorController

}  // namespace s21

#endif