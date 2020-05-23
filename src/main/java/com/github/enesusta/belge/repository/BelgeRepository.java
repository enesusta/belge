package com.github.enesusta.belge.repository;

import java.util.concurrent.CompletableFuture;

public interface BelgeRepository {
   byte[] getSource(int owner, String fileName);
   void save(byte[] arr);
}
