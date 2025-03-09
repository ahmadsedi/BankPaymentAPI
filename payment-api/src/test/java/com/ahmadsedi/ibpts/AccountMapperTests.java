package com.ahmadsedi.ibpts;

import com.ahmadsedi.ibpts.controller.AccountMapper;
import com.ahmadsedi.ibpts.data.entity.AccountEntity;
import com.ahmadsedi.ibpts.vo.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 28/02/2025
 * Time: 16:12
 */

class AccountMapperTests {

    AccountMapper accountMapper = new AccountMapper();

    @Test
    public void apiToEntityTests(){
        Account request = new Account();
        request.setBalance(10);
        AccountEntity accountEntity = accountMapper.apiToEntity(request);
        Assertions.assertEquals(request.getBalance(), accountEntity.getBalance(), "Balance not converted.");
    }

}
