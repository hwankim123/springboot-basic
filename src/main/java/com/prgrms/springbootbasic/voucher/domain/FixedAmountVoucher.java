package com.prgrms.springbootbasic.voucher.domain;

import static com.prgrms.springbootbasic.common.exception.ExceptionMessage.ILLEGAL_STATE_EXCEPTION_WHEN_DISCOUNT;

import com.prgrms.springbootbasic.common.exception.AmountOutOfBoundException;
import com.prgrms.springbootbasic.common.exception.VoucherErrorCode;
import com.prgrms.springbootbasic.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private static final int MAX_AMOUNT_BOUNDARY = 10000;
    private static final int MIN_AMOUNT_BOUNDARY = 1;
    private static final Logger logger = LoggerFactory.getLogger(FixedAmountVoucher.class);

    private final UUID id;
    private int fixedAmount;
    private VoucherType voucherType;

    public FixedAmountVoucher(int fixedAmount) {
        validate(fixedAmount);
        this.id = UUID.randomUUID();
        this.fixedAmount = fixedAmount;
    }

    public FixedAmountVoucher(UUID uuid, int fixedAmount) {
        validate(fixedAmount);
        this.id = uuid;
        this.fixedAmount = fixedAmount;
    }

    public FixedAmountVoucher(UUID uuid, int fixedAmount, VoucherType voucherType) {
        this(uuid, fixedAmount);
        this.voucherType = voucherType;
    }

    public FixedAmountVoucher(int fixedAmount, VoucherType voucherType) {
        this(fixedAmount);
        this.voucherType = voucherType;
    }

    @Override
    public void validate(int discountAmount) {
        if (discountAmount < MIN_AMOUNT_BOUNDARY || discountAmount > MAX_AMOUNT_BOUNDARY) {
            logger.warn("AmountOutOfBoundException occurred when creating new Voucher. Amount dut of boundary.");
            throw new AmountOutOfBoundException(
                    this.getClass().getSimpleName(),
                    discountAmount,
                    MIN_AMOUNT_BOUNDARY,
                    MAX_AMOUNT_BOUNDARY,
                    VoucherErrorCode.AMOUNT_OUT_OF_BOUND);
        }
    }

    @Override
    public UUID getUUID() {
        return id;
    }

    @Override
    public int getDiscountAmount() {
        return fixedAmount;
    }

    @Override
    public VoucherType getVoucherType() {
        return voucherType;
    }

    @Override
    public BigDecimal discount(int beforeDiscount) {
        BigDecimal afterDiscount = new BigDecimal(beforeDiscount - fixedAmount);
        if (afterDiscount.compareTo(BigDecimal.ZERO) <= -1) {
            throw new IllegalStateException(ILLEGAL_STATE_EXCEPTION_WHEN_DISCOUNT);
        }
        return afterDiscount;
    }

    @Override
    public void update(int discountAmount) {
        validate(discountAmount);
        this.fixedAmount = discountAmount;
    }
}
