package uk.gov.hmcts.reform.unspec.service.search;

import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.unspec.model.search.Query;
import uk.gov.hmcts.reform.unspec.service.CoreCaseDataService;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;

@Service
public class CaseStayedSearchService extends ElasticSearchService {

    public CaseStayedSearchService(CoreCaseDataService coreCaseDataService) {
        super(coreCaseDataService);
    }

    //TODO: update case data to store claimIssuedDate + 4 months. ES query can then be done on this value using now
    public Query query(int startIndex) {
        return new Query(
            boolQuery()
                .must(rangeQuery("data.claimIssuedDate").lt("now-112d"))
                .must(matchQuery("state", "CREATED")),
            List.of("reference"),
            startIndex
        );
    }
}
