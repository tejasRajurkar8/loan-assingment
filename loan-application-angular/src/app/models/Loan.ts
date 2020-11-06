export interface Loan{
    //loanId: number;
    custId: number;
    loanAmount: number;
    interestRate: number;
    paymentFrequency: number;
    tenure: number;
    tradeDate: Date;
    loanStartDate: Date;
    maturityDate: Date;
    evenPrincipal: boolean

}