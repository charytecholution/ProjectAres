package tc.oc.api.ocn;

import java.util.Collection;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.common.util.concurrent.ListenableFuture;
import tc.oc.api.docs.Server;
import tc.oc.api.docs.virtual.ServerDoc;
import tc.oc.api.http.HttpOption;
import tc.oc.api.message.types.FindMultiResponse;
import tc.oc.api.message.types.FindRequest;
import tc.oc.api.model.HttpModelService;
import tc.oc.api.queue.QueueQueryService;
import tc.oc.api.servers.BungeeMetricRequest;
import tc.oc.api.servers.ServerService;

@Singleton
class OCNServerService extends HttpModelService<Server, ServerDoc.Partial> implements ServerService {

    private final QueueQueryService<Server> queryService;

    @Inject OCNServerService(QueueQueryService<Server> queryService) {
        this.queryService = queryService;
    }

    @Override
    public ListenableFuture<?> doBungeeMetric(BungeeMetricRequest request) {
        return this.client().post("/servers/metric", request, HttpOption.INFINITE_RETRY);
    }

    @Override
    public ListenableFuture<FindMultiResponse<Server>> all() {
        return queryService.all();
    }

    @Override
    public ListenableFuture<Server> find(String id) {
        return queryService.find(id);
    }

    @Override
    public ListenableFuture<FindMultiResponse<Server>> find(FindRequest<Server> request) {
        return queryService.find(request);
    }

    @Override
    public ListenableFuture<FindMultiResponse<Server>> find(Collection<String> ids) {
        return queryService.find(ids);
    }
}
