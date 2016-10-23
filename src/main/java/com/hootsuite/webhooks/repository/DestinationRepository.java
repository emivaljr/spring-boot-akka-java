package com.hootsuite.webhooks.repository;

import com.hootsuite.webhooks.domain.Destination;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by emival on 23/10/16.
 */
public interface DestinationRepository extends CrudRepository<Destination,Long> {
}
