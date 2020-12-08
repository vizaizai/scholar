package com.github.vizaizai.scholar.infrastructure.persistence.elastic;

import com.github.vizaizai.scholar.infrastructure.persistence.dataobject.BookDo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author liaochongwei
 * @date 2020/7/23 15:20
 */
public interface BookRepository extends ElasticsearchRepository<BookDo, String> {

    List<BookDo> findByNameAndDescription(String name, String description);
}
