package com.sh.shpay.domain.openbanking.token.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOpenBankingToken is a Querydsl query type for OpenBankingToken
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOpenBankingToken extends EntityPathBase<OpenBankingToken> {

    private static final long serialVersionUID = 1917240157L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOpenBankingToken openBankingToken = new QOpenBankingToken("openBankingToken");

    public final com.sh.shpay.domain.common.QBaseTimeEntity _super = new com.sh.shpay.domain.common.QBaseTimeEntity(this);

    public final StringPath accessToken = createString("accessToken");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> expireMin = createNumber("expireMin", Long.class);

    public final StringPath refreshToken = createString("refreshToken");

    public final NumberPath<Long> tokenId = createNumber("tokenId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.sh.shpay.domain.users.domain.QUsers users;

    public final StringPath userSeqNo = createString("userSeqNo");

    public QOpenBankingToken(String variable) {
        this(OpenBankingToken.class, forVariable(variable), INITS);
    }

    public QOpenBankingToken(Path<? extends OpenBankingToken> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOpenBankingToken(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOpenBankingToken(PathMetadata metadata, PathInits inits) {
        this(OpenBankingToken.class, metadata, inits);
    }

    public QOpenBankingToken(Class<? extends OpenBankingToken> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.users = inits.isInitialized("users") ? new com.sh.shpay.domain.users.domain.QUsers(forProperty("users"), inits.get("users")) : null;
    }

}

