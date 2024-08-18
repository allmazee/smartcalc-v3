#include "calculator_model.h"

#include <cmath>
#include <iostream>
#include <vector>
namespace s21 {
void CalculatorModel::set_infix(const std::string &infix) { infix_ = infix; }

void CalculatorModel::set_x(const double &x) { x_ = x; }

CalculatorModel::Status CalculatorModel::get_status() const { return status_; }

double CalculatorModel::get_result() const { return result_; }

void CalculatorModel::Calculate() {
  status_ = ToPostfix();
  if (status_ == Status::ERROR) {
    // throw std::invalid_argument("Invalid Input");
    return;
  }
  for (auto it = postfix_.begin();
       it != postfix_.end() && status_ == Status::OK; it++) {
    if (IsNum(it)) {
      std::string numString;
      while (IsNum(it)) {
        numString += *it++;
      }
      stack_double_.push(std::stod(numString));
    } else if (*it == 'x') {
      stack_double_.push(x_);
    } else {
      std::string op(1, *it);
      if (IsOperator(it)) {
        CalculateOperation(it);
      } else if (IsFunction(it)) {
        CalculateFunction(it);
      }
    }
  }
  result_ = stack_double_.top();
}

void CalculatorModel::CalculateOperation(std::string::iterator &it) {
  double num2;
  num2 = stack_double_.top();
  stack_double_.pop();
  if (*it == '~' || *it == '&') {
    *it == '~' ? stack_double_.push(-num2) : stack_double_.push(num2);
    return;
  }
  double num1;
  if (!stack_double_.empty()) {
    num1 = stack_double_.top();
    stack_double_.pop();
  } else {
    status_ = Status::ERROR;
    return;
  }
  //  num1 = 0;
  if (*it == '+') {
    stack_double_.push(num1 + num2);
  } else if (*it == '-') {
    stack_double_.push(num1 - num2);
  } else if (*it == '*') {
    stack_double_.push(num1 * num2);
  } else if (*it == '/') {
    if (num2 == 0) status_ = Status::ERROR;
    stack_double_.push(num1 / num2);
  } else if (*it == '%') {
    stack_double_.push(fmod(num1, num2));
  } else if (*it == '^') {
    stack_double_.push(pow(num1, num2));
  }
}

void CalculatorModel::CalculateFunction(std::string::iterator &it) {
  double num;
  num = stack_double_.top();
  stack_double_.pop();
  std::string function;
  while (*it != ' ') {
    function += *it++;
  }
  if (function == "sin") {
    stack_double_.push(sin(num));
  } else if (function == "cos") {
    stack_double_.push(cos(num));
  } else if (function == "tan") {
    stack_double_.push(tan(num));
  } else if (function == "acos") {
    stack_double_.push(acos(num));
  } else if (function == "asin") {
    stack_double_.push(asin(num));
  } else if (function == "atan") {
    stack_double_.push(atan(num));
  } else if (function == "sqrt") {
    if (num < 0) {
      status_ = Status::ERROR;
    }
    stack_double_.push(sqrt(num));
  } else if (function == "ln") {
    if (num <= 0) status_ = Status::ERROR;
    stack_double_.push(log(num));
  } else if (function == "log") {
    if (num <= 0) status_ = Status::ERROR;
    stack_double_.push(log10(num));
  }
}

// Приоритет выше - пуш, Приоритет ниже или равен - выполнение прошлой операции
CalculatorModel::Status CalculatorModel::ToPostfix() {
  postfix_ = "";
  result_ = 0;
  Status status = Status::OK;
  if (!IsValid()) return Status::ERROR;
  for (auto it = infix_.begin(); it != infix_.end(); ++it) {
    if (*it == ' ') {
      continue;
    } else if (*it == 'x') {
      XToPostfix();
    } else if (IsNum(it)) {
      status = NumToPostfix(it);
    } else if (IsOperator(it)) {
      status = OperatorToPostfix(it);
    } else if (IsFunction(it)) {
      status = FunctionToPostfix(it);
    } else if (IsBrackets(it)) {
      status = BracketsToPostfix(it);
    }
    if (status == Status::ERROR) return status;
  }
  while (!stack_string_.empty()) {
    postfix_.append(stack_string_.top() + ' ');
    stack_string_.pop();
  }
  return status;
}

CalculatorModel::Status CalculatorModel::NumToPostfix(
    std::string::iterator &it) {
  int dot_count = 0;
  while (IsNum(it) && dot_count <= 1) {
    if (*it == '.') dot_count++;
    postfix_.push_back(*it);
    ++it;
  }
  if (postfix_.back() == '.') return Status::ERROR;
  --it;
  postfix_.push_back(' ');
  return (dot_count > 1) ? Status::ERROR : Status::OK;
}

CalculatorModel::Status CalculatorModel::XToPostfix() {
  postfix_.append("x ");
  return Status::OK;
}

CalculatorModel::Status CalculatorModel::OperatorToPostfix(
    std::string::iterator &it) {
  auto next = it + 1;
  if (IsOperator(next) || next == infix_.end())
    return Status::ERROR;
  else if (!IsUnary(it) && it == infix_.begin())
    return Status::ERROR;
  std::string op(1, *it);
  if (IsUnary(it)) {
    op = *it == '-' ? "~" : "&";
  }
  while (!stack_string_.empty() &&
         GetPriority(op) <= GetPriority(stack_string_.top())) {
    postfix_.append(stack_string_.top() + ' ');
    stack_string_.pop();
  }
  stack_string_.push(op);
  return Status::OK;
}

CalculatorModel::Status CalculatorModel::FunctionToPostfix(
    std::string::iterator &it) {
  std::vector<std::string> function = {"sin",  "cos",  "tan", "asin", "acos",
                                       "atan", "sqrt", "ln",  "log"};
  for (long unsigned int i = 0; i < function.size(); i++) {
    if (std::equal(function[i].begin(), function[i].end(), it,
                   it + function[i].size())) {
      stack_string_.push(function[i]);
      it += function[i].size() - 1;
    }
  }
  if (*(it + 1) != '(') return Status::ERROR;
  return Status::OK;
}

CalculatorModel::Status CalculatorModel::BracketsToPostfix(
    std::string::iterator &it) {
  if (*it == '(') {
    auto next = it + 1;
    if (*next == ')') return Status::ERROR;
    std::string op(1, *it);
    stack_string_.push(op);
  } else {
    while (!stack_string_.empty() && stack_string_.top() != "(") {
      postfix_.append(stack_string_.top() + ' ');
      stack_string_.pop();
    }
    if (!stack_string_.empty() && stack_string_.top() == "(")
      stack_string_.pop();
    else
      return Status::ERROR;
  }
  return Status::OK;
}

bool CalculatorModel::IsValid() {
  if (infix_.empty()) return false;
  int open_braces = 0, close_braces = 0;
  std::string validated_symbols = "1234567890x()+-*/^\%sincosatandqrlg. ";
  for (auto it = infix_.begin(); it != infix_.end(); ++it) {
    if (validated_symbols.find(*it) == std::string::npos) {
      return false;
    }
    if (IsBrackets(it)) {
      *it == '(' ? open_braces++ : close_braces++;
    }
  }
  return open_braces == close_braces;
}

bool CalculatorModel::IsNum(std::string::iterator &it) {
  return isdigit(*it) || *it == '.';
}

bool CalculatorModel::IsUnary(std::string::iterator &it) {
  return (*it == '-' || *it == '+') &&
         (it == infix_.begin() || *(it - 1) == '(');
}

bool CalculatorModel::IsOperator(std::string::iterator &it) {
  return *it == '+' || *it == '-' || *it == '~' || *it == '*' || *it == '/' ||
         *it == '%' || *it == '^';
}

bool CalculatorModel::IsFunction(std::string::iterator &it) {
  return *it == 's' || *it == 'a' || *it == 'c' || *it == 't' || *it == 'l';
}

bool CalculatorModel::IsBrackets(std::string::iterator &it) {
  return *it == '(' || *it == ')';
}

CalculatorModel::Priority CalculatorModel::GetPriority(std::string ch) {
  Priority priority = Priority::FUNCTION;
  if (ch == "(" || ch == ")") {
    priority = Priority::BRACKET;
  } else if (ch == "+" || ch == "-" || ch == "~" || ch == "&") {
    priority = Priority::PLUSMINUS;
  } else if (ch == "*" || ch == "/" || ch == "%") {
    priority = Priority::MULTDIVMOD;
  } else if (ch == "^") {
    priority = Priority::POW;
  }
  return priority;
}

}  // namespace s21
