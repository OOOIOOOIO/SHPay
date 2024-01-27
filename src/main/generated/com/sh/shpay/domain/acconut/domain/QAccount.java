package com.sh.shpay.domain.acconut.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAccount is a Querydsl query type for Account
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccount extends EntityPathBase<Account> {

    private static final long serialVersionUID = 556147669L;

    public static final QAccount account = new QAccount("account");

    public final com.sh.shpay.domain.common.QBaseTimeEntity _super = new com.sh.shpay.domain.common.QBaseTimeEntity(this);

    public final NumberPath<Long> accountId = createNumber("accountId", Long.class);

    public final StringPath accountNum = createString("accountNum");

    public final StringPath accountSeq = createString("accountSeq");

    public final EnumPath<AccountType> accountType = createEnum("accountType", AccountType.class);

    public final StringPath bankCode = createString("bankCode");

    public final StringPath bankName = createString("bankName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath fintechUseNum = createString("fintechUseNum");

    public final StringPath holderName = createString("holderName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QAccount(String variable) {
        super(Account.class, forVariable(variable));
    }

    public QAccount(Path<? extends Account> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccount(PathMetadata metadata) {
        super(Account.class, metadata);
    }

}

