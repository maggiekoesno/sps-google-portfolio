// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.ArrayList; 
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;   
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class FindMeetingQuery {
    /**
    * Time complexity is O(nm + nlogn) where n is the number of events and m is the number of attendees in the MeetingRequest
    **/
    public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
        // no options for requests longer than a day
        if (request.getDuration() > TimeRange.WHOLE_DAY.duration()) {
            return new ArrayList();
        }

        // Time complexity: O(nm + nlogn)
        Collection<TimeRange> unavailableTimes = getUnavailableTimes(events, request.getAttendees());

        // Time complexity: O(n)
        return findAvailableTimes(unavailableTimes, request.getDuration());
    }

    /**
    * This function returns a sorted list of TimeRange objects from events that the attendees in the meeting request are attending
    * Time complexity is O(nm + nlogn) where n is the number of events and m is the number of attendees
    **/
    private Collection<TimeRange> getUnavailableTimes(Collection<Event> events, Collection<String> attendees){
        ArrayList<TimeRange> unavailableTimes = new ArrayList<TimeRange>();

        // Time complexity: O(nm)
        for(Event event: events){
            Set<String> eventAttendees = new HashSet<String>(event.getAttendees());
            eventAttendees.retainAll(attendees);
            if (!eventAttendees.isEmpty()) {
                unavailableTimes.add(event.getWhen());
            }
        }

        // Time complexity: O(nlogn)
        Collections.sort(unavailableTimes, TimeRange.ORDER_BY_START);

        return unavailableTimes;
    }


    /**
    * This function returns a list of available times in a day 
    * Time complexity is O(n) where n is the number of events
    **/
    private Collection<TimeRange> findAvailableTimes(Collection<TimeRange> unavailableTimes, long duration){
        Collection<TimeRange> availableTimes = new ArrayList<TimeRange>();
        int prevEndTime = TimeRange.START_OF_DAY;

        for(TimeRange time : unavailableTimes){
            if (time.start() - prevEndTime >= duration){
                availableTimes.add(TimeRange.fromStartEnd(prevEndTime, time.start(), false));
            }
            if (time.end() > prevEndTime){
                prevEndTime = time.end();
            }
        }

        if (TimeRange.END_OF_DAY - prevEndTime >= duration) {
            availableTimes.add(TimeRange.fromStartEnd(prevEndTime, TimeRange.END_OF_DAY, true));
        }

        return availableTimes;
    }
}
