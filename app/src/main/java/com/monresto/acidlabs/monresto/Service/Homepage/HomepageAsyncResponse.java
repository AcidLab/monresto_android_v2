package com.monresto.acidlabs.monresto.Service.Homepage;

import com.monresto.acidlabs.monresto.Model.HomepageConfig;
import com.monresto.acidlabs.monresto.Model.HomepageDish;
import com.monresto.acidlabs.monresto.Model.HomepageEvent;

import java.util.ArrayList;

public interface HomepageAsyncResponse {
    void onHomepageConfigReceived(HomepageConfig config);
    void onHomepageEventsReceived(ArrayList<HomepageEvent> events);
    void onHomepageDishesReceived(ArrayList<HomepageDish> dishes);
}
