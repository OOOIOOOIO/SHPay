package com.sh.shpay.domain.acconut.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccount is a Querydsl query type for Account
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccount extends EntityPathBase<Account> {

    private static final long serialVersionUID = 556147669L;

    private static final PathInits INITS = PathInits.DIRECT2;

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

    public final com.sh.shpay.domain.users.domain.QUsers users;

    public QAccount(String variable) {
        this(Account.class, forVariable(variable), INITS);
    }

    public QAccount(Path<? extends Account> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAccount(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAccount(PathMetadata metadata, PathInits inits) {
        this(Account.class, metadata, inits);
    }

    public QAccount(Class<? extends Account> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.users = inits.isInitialized("users") ? new com.sh.shpay.domain.users.domain.QUsers(forProperty("users"), inits.get("users")) : null;
    }

}

