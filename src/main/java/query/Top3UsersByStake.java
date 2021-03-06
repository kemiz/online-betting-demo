package query;

import dao.UserDao;
import model.User;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.configuration.IgniteConfiguration;
import utils.IgniteConfigHelper;

import java.util.List;

public class Top3UsersByStake {
    public static void main(String[] args) throws InterruptedException {
        IgniteConfiguration cfg = IgniteConfigHelper.getIgniteClientConfig();
        Ignite ignite = Ignition.start(cfg);

        IgniteCache<Long, User> userCache = ignite.cache(IgniteConfigHelper.USER_CACHE);

        SqlFieldsQuery sql = new SqlFieldsQuery(
                "select top 3 name, stake from User order by stake desc");

        while (true) {
            try {
                // Iterate over the result set.
                try (QueryCursor<List<?>> cursor = userCache.query(sql)) {
                    for (List<?> row : cursor)
                        System.out.println("Name: " + row.get(0) + ", Total Stake: " + row.get(1));
                } catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("\n\n\n\n\n");
                Thread.sleep(1000);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
