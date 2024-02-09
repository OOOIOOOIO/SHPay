package com.sh.shpay.domain.users.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUsers is a Querydsl query type for Users
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsers extends EntityPathBase<Users> {

    private static final long serialVersionUID = -1971929145L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUsers users = new QUsers("users");

    public final com.sh.shpay.domain.common.QBaseTimeEntity _super = new com.sh.shpay.domain.common.QBaseTimeEntity(this);

    public final ListPath<com.sh.shpay.domain.acconut.domain.Account, com.sh.shpay.domain.acconut.domain.QAccount> accountList = this.<com.sh.shpay.domain.acconut.domain.Account, com.sh.shpay.domain.acconut.domain.QAccount>createList("accountList", com.sh.shpay.domain.acconut.domain.Account.class, com.sh.shpay.domain.acconut.domain.QAccount.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final StringPath openBankCi = createString("openBankCi");

    public final com.sh.shpay.domain.openbanking.token.domain.QOpenBankingToken openBankingToken;

    public final StringPath password = createString("password");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath userSeqNo = createString("userSeqNo");

    public QUsers(String variable) {
        this(Users.class, forVariable(variable), INITS);
    }

    public QUsers(Path<? extends Users> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUsers(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUsers(PathMetadata metadata, PathInits inits) {
        this(Users.class, metadata, inits);
    }

    public QUsers(Class<? extends Users> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.openBankingToken = inits.isInitialized("openBankingToken") ? new com.sh.shpay.domain.openbanking.token.domain.QOpenBankingToken(forProperty("openBankingToken"), inits.get("openBankingToken")) : null;
    }

}

