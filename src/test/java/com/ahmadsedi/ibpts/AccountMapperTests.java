package com.ahmadsedi.ibpts;

import com.ahmadsedi.ibpts.controller.AccountMapper;
import com.ahmadsedi.ibpts.data.entity.AccountEntity;
import com.ahmadsedi.ibpts.vo.AccountCreationRequest;
import com.ahmadsedi.ibpts.vo.AccountCreationResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * @author Ahmad R. Seddighi (ahmadseddighi@yahoo.com)
 * Date: 28/02/2025
 * Time: 16:12
 */

public class AccountMapperTests {

    AccountMapper accountMapper = new AccountMapper();

    @Test
    public void apiToEntityTests(){
        AccountCreationRequest request = new AccountCreationRequest();
        request.setBalance(10);
        AccountEntity accountEntity = accountMapper.apiToEntity(request);
        Assertions.assertEquals(request.getBalance(), accountEntity.getBalance(), "Balance not converted.");
    }

    @Test
    public void entityToApiTests(){
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setBalance(10);
        accountEntity.setCreated(new Date());
        accountEntity.setVersion(1);
        accountEntity.setId(1);
        AccountCreationResponse response = accountMapper.entityToApi(accountEntity);
        Assertions.assertEquals(accountEntity.getCreated().toString(), response.getCreated(), "Date not converted properly.");
        Assertions.assertEquals(accountEntity.getBalance(), response.getBalance(), "Balance not converted.");
        Assertions.assertEquals(accountEntity.getId(), response.getAccountId(), "Account id not converted.");
    }

}
