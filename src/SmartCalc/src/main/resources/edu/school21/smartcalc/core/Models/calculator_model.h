#ifndef CALCULATOR_MODEL_H
#define CALCULATOR_MODEL_H

#include <stack>
#include <string>

namespace s21 {
class CalculatorModel {
 public:
  enum class Status { OK, ERROR };

  enum class Priority {
    NONE = -1,
    BRACKET,
    PLUSMINUS,
    MULTDIVMOD,
    POW,
    FUNCTION
  };

  CalculatorModel(){};
  ~CalculatorModel(){};

  void set_infix(const std::string &infix);
  void set_x(const double &x);
  Status get_status() const;
  double get_result() const;

  void Calculate();

 private:
  double x_ = 0;
  double result_;
  Status status_;
  std::string infix_ = "";
  std::string postfix_;
  std::stack<double> stack_double_;
  std::stack<std::string> stack_string_;

  void CalculateOperation(std::string::iterator &it);
  void CalculateFunction(std::string::iterator &it);

  enum Status ToPostfix();
  enum Status NumToPostfix(std::string::iterator &it);
  enum Status XToPostfix();
  enum Status OperatorToPostfix(std::string::iterator &it);
  enum Status FunctionToPostfix(std::string::iterator &it);
  enum Status BracketsToPostfix(std::string::iterator &it);

  bool IsValid();
  bool IsNum(std::string::iterator &it);
  bool IsUnary(std::string::iterator &it);
  bool IsOperator(std::string::iterator &it);
  bool IsFunction(std::string::iterator &it);
  bool IsBrackets(std::string::iterator &it);
  enum Priority GetPriority(std::string ch);
};
}  // namespace s21

#endif