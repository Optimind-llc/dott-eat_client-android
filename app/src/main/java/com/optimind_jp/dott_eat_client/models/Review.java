package com.optimind_jp.dott_eat_client.models;

import java.util.UUID;

/**
 * Created by hugh on 2016-08-22.
 */

public abstract class Review extends Transaction {
    UUID author;
    String contents;
}
