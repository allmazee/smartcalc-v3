#include "credit_model.h"

#include <cmath>
#include <iostream>

namespace s21 {
CreditModel::CreditType CreditModel::get_credit_type() const {
  return credit_type_;
}

double CreditModel::get_amount() const { return credit_amount_; }

int CreditModel::get_term() const { return credit_term_; }

int CreditModel::get_rate() const { return credit_rate_; }

std::vector<double> CreditModel::get_monthly_payment() const {
  return monthly_payment_;
}

double CreditModel::get_overpayment() const { return overpayment_; }

double CreditModel::get_total_payment() const { return total_payment_; }

void CreditModel::set_credit_type(CreditType type) { credit_type_ = type; }

void CreditModel::set_amount(double amount) { credit_amount_ = amount; }

void CreditModel::set_term(int term) { credit_term_ = term; }

void CreditModel::set_rate(int rate) { credit_rate_ = rate; }

void CreditModel::Calculate() {
  monthly_payment_.clear();
  total_payment_ = 0;
  if (credit_type_ == CreditType::ANNUITY) {
    AnnuityPayment();
  } else if (credit_type_ == CreditType::DIFF) {
    DifferentiatedPayment();
  }
  overpayment_ = total_payment_ - credit_amount_;
}

void CreditModel::AnnuityPayment() {
  double monthly_rate = credit_rate_ / 12 / 100;
  double factor = pow(1 + monthly_rate, credit_term_);
  double annuity_payment =
      credit_amount_ * (monthly_rate * factor) / (factor - 1);
  monthly_payment_.push_back(annuity_payment);
  total_payment_ = monthly_payment_.back() * credit_term_;
}

void CreditModel::DifferentiatedPayment() {
  double monthly_rate = credit_rate_ / 12 / 100;
  for (int i = 1; i <= credit_term_; i++) {
    double base_monthly_payment = credit_amount_ / credit_term_;
    double rate_monthly_payment =
        (credit_amount_ - (i - 1) * base_monthly_payment) * monthly_rate;
    double diff_payment = base_monthly_payment + rate_monthly_payment;
    total_payment_ += diff_payment;
    monthly_payment_.push_back(diff_payment);
  }
}

}  // namespace s21
