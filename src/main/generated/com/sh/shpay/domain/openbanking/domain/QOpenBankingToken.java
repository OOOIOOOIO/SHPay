package com.sh.shpay.domain.openbanking.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOpenBankingToken is a Querydsl query type for OpenBankingToken
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOpenBankingToken extends EntityPathBase<OpenBankingToken> {

    private static final long serialVersionUID = -806935406L;

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

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath userSeqNo = createString("userSeqNo");

    public QOpenBankingToken(String variable) {
        super(OpenBankingToken.class, forVariable(variable));
    }

    public QOpenBankingToken(Path<? extends OpenBankingToken> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOpenBankingToken(PathMetadata metadata) {
        super(OpenBankingToken.class, metadata);
    }

}

