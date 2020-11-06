export interface Schedule{
    scheduleId: number;
    loanId: number;
    paymentDate: Date;
    principal: number;
    projectedInterest: number;
    paymentAmount: number;
    status: string;
}