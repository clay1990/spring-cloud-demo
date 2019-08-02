package com.clay.search.impl;

import com.alibaba.fastjson.JSON;
import com.clay.constant.HouseSourceIndex;
import com.clay.es.dto.HouseSourceDto;
import com.clay.model.HouseSourceSuggest;
import com.clay.model.House_source;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;


/**
 * @Auther: yuyao
 * @Date: 2019/7/25 16:37
 * @Description:
 */
@Service
public class ServiceServiceImpl implements ISearchService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceServiceImpl.class);


    @Autowired
    TransportClient client;

    @Override
    public void save(House_source house_source) {
        List<HouseSourceSuggest> suggest = new ArrayList<>();
//        if(!StringUtils.isEmpty(house_source.getVillage()) || !StringUtils.isEmpty(house_source.getTags()) ){
//            AnalyzeRequestBuilder requestBuilder = new AnalyzeRequestBuilder(this.client, AnalyzeAction.INSTANCE,HouseSourceIndex.INDEX_NAME,
//                    StringUtils.isEmpty(house_source.getVillage()) ? "":house_source.getVillage(),
//                    StringUtils.isEmpty(house_source.getTags()) ? "": house_source.getTags());
//
//            requestBuilder.setAnalyzer("ik_smart");
//
//            AnalyzeResponse analyzeTokens = requestBuilder.get();
//            for (AnalyzeResponse.AnalyzeToken analyzeToken : analyzeTokens) {
//                if("<NUM>".equals(analyzeToken.getType()) || analyzeToken.getTerm().length() < 2){
//                    continue;
//                }
//
//                HouseSourceSuggest houseSourceSuggest = new HouseSourceSuggest();
//                houseSourceSuggest.setInput(analyzeToken.getTerm());
//
//                suggest.add(houseSourceSuggest);
//                house_source.setSuggest(suggest);
//            }
//        }

        if(!StringUtils.isEmpty(house_source.getVillage())){
            HouseSourceSuggest villageSuggest = new HouseSourceSuggest();
            villageSuggest.setInput(house_source.getVillage());
            suggest.add(villageSuggest);
        }
        if(!StringUtils.isEmpty(house_source.getTags())){
            String[] split = house_source.getTags().split(",");
            for (String tag : split) {
                HouseSourceSuggest tagSuggest = new HouseSourceSuggest();
                tagSuggest.setInput(tag);
                suggest.add(tagSuggest);
            }
        }

        if(!StringUtils.isEmpty(house_source.getRegion())){
            HouseSourceSuggest houseSourceSuggest1 = new HouseSourceSuggest();
            houseSourceSuggest1.setInput(house_source.getRegion());
            suggest.add(houseSourceSuggest1);
        }

        if(!StringUtils.isEmpty(house_source.getRegion1())){
            HouseSourceSuggest houseSourceSuggest2 = new HouseSourceSuggest();
            houseSourceSuggest2.setInput(house_source.getRegion1());
            suggest.add(houseSourceSuggest2);
        }

        house_source.setSuggest(suggest);

        IndexResponse indexResponse = this.client.prepareIndex(HouseSourceIndex.INDEX_NAME, HouseSourceIndex.TYPE_NAME).setSource(
                JSON.toJSONString(house_source), XContentType.JSON
        ).get();
        if(indexResponse.status() != RestStatus.OK){
//            logger.error("save error : " + house_source);
        }
    }

    @Override
    public List<HouseSourceDto> queryByCondition(House_source house_source) {
        List<HouseSourceDto> result = new ArrayList<>();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if(StringUtils.hasText(house_source.getVillage())){
            boolQueryBuilder.filter(
                    QueryBuilders.matchQuery("title",house_source.getVillage())
            );
        }
        SearchRequestBuilder requestBuilder = this.client.prepareSearch(HouseSourceIndex.INDEX_NAME).setTypes(HouseSourceIndex.TYPE_NAME)
                .setQuery(boolQueryBuilder)
                .addSort(HouseSourceIndex.MAX_PRICE, SortOrder.DESC)
                .setSize(10);
        SearchResponse searchResponse = requestBuilder.get();
        if(searchResponse.status() != RestStatus.OK){
            logger.error("查询失败： " + requestBuilder);
            return result;
        }

        for (SearchHit hit : searchResponse.getHits()) {
            result.add(JSON.parseObject(hit.getSourceAsString(), HouseSourceDto.class));
        }
        return result;
    }

    @Override
    public List<HouseSourceDto> queryByKeyWord(String keyWord) {
        List<HouseSourceDto> result = new ArrayList<>();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.filter(
                QueryBuilders.multiMatchQuery(keyWord,
                        HouseSourceIndex.VILLAGE,
                        HouseSourceIndex.REGION,
                        HouseSourceIndex.REGION1,
                        HouseSourceIndex.TAGS
                        )
        );
        SearchRequestBuilder requestBuilder = this.client.prepareSearch(HouseSourceIndex.INDEX_NAME).setTypes(HouseSourceIndex.TYPE_NAME)
                .setQuery(boolQueryBuilder)
                .addSort(HouseSourceIndex.MAX_PRICE, SortOrder.DESC)
                .setSize(10);
        SearchResponse searchResponse = requestBuilder.get();
        if(searchResponse.status() != RestStatus.OK){
            logger.error("查询失败： " + requestBuilder);
            return result;
        }

        for (SearchHit hit : searchResponse.getHits()) {
            result.add(JSON.parseObject(hit.getSourceAsString(), HouseSourceDto.class));
        }
        return result;
    }

    @Override
    public List<String> sugget(String prefix) {
//        try {
//            Thread.sleep(3000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        CompletionSuggestionBuilder suggestion = SuggestBuilders.completionSuggestion("suggest").prefix(prefix).skipDuplicates(true).size(100);

        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("autoComplete",suggestion);

        SearchRequestBuilder request = this.client.prepareSearch(HouseSourceIndex.INDEX_NAME).setTypes(HouseSourceIndex.TYPE_NAME).suggest(suggestBuilder);
        SearchResponse searchResponse = request.get();
        Suggest suggest = searchResponse.getSuggest();

        Suggest.Suggestion autoComplete = suggest.getSuggestion("autoComplete");

        Set<String> suggestSet = new HashSet<>();
        for (Object entry : autoComplete.getEntries()) {
            if(entry instanceof CompletionSuggestion.Entry){
                CompletionSuggestion.Entry item = (CompletionSuggestion.Entry) entry;
                if(item.getOptions().isEmpty()){
                    continue;
                }

                for (CompletionSuggestion.Entry.Option option : item.getOptions()) {
                    String tip = option.getText().string();
                    if(suggestSet.contains(tip)){
                        continue;
                    }
                    suggestSet.add(tip);
                }
            }
        }

        String[] strings = suggestSet.toArray(new String[]{});
        return Arrays.asList(strings);
    }
}