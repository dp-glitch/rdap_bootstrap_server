/*
 * Copyright (C) 2013 American Registry for Internet Numbers (ARIN)
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */
package net.arin.rdap_bootstrap;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @version $Rev$, $Date$
 */
public class Statistics
{
    private AtomicLong totalHits = new AtomicLong( 0 );
    private AtomicLong totalMisses = new AtomicLong( 0 );
    private HashMap<String,AtomicLong> asRirHits = new HashMap<String, AtomicLong>(  );
    private HashMap<String,AtomicLong> ip4RirHits = new HashMap<String, AtomicLong>(  );
    private HashMap<String,AtomicLong> ip6RirHits = new HashMap<String, AtomicLong>(  );
    private HashMap<String,AtomicLong> entityRirHits = new HashMap<String, AtomicLong>(  );
    private HashMap<String,AtomicLong> domainRirHits = new HashMap<String, AtomicLong>(  );
    private HashMap<String,AtomicLong> domainTldHits = new HashMap<String, AtomicLong>(  );
    private HashMap<String,AtomicLong> nsTldHits = new HashMap<String, AtomicLong>(  );

    private RirMap rirMap = new RirMap();

    public Statistics() throws Exception
    {
        rirMap.loadData();
        rirMap.addEntityRirCountersToStatistics( this );
    }

    private void incrementRirCounter( HashMap<String, AtomicLong> hashMap, String key )
    {
        String rir = rirMap.getRirFromUrl( key );
        if( key != null )
        {
            AtomicLong hits = hashMap.get( rir );
            if( hits != null )
            {
                hits.incrementAndGet();
                totalHits.incrementAndGet();
            }
            else
            {
                totalMisses.incrementAndGet();
            }
        }
    }

    public HitCounter getAsHitCounter()
    {
        class AsHitCounter implements HitCounter
        {
            public void incrementCounter( String url )
            {
                incrementRirCounter( asRirHits, url );
            }
        }
        return new AsHitCounter();
    }

    public HitCounter getIp4RirHitCounter()
    {
        class AsHitCounter implements HitCounter
        {
            public void incrementCounter( String url )
            {
                incrementRirCounter( ip4RirHits, url );
            }
        }
        return new AsHitCounter();
    }

    public HitCounter getIp6RirHitCounter()
    {
        class AsHitCounter implements HitCounter
        {
            public void incrementCounter( String url )
            {
                incrementRirCounter( ip6RirHits, url );
            }
        }
        return new AsHitCounter();
    }

    public HitCounter getEntityRirHitCounter()
    {
        class AsHitCounter implements HitCounter
        {
            public void incrementCounter( String url )
            {
                incrementRirCounter( entityRirHits, url );
            }
        }
        return new AsHitCounter();
    }

    public HitCounter getDomainRirHitCounter()
    {
        class AsHitCounter implements HitCounter
        {
            public void incrementCounter( String url )
            {
                incrementRirCounter( entityRirHits, url );
            }
        }
        return new AsHitCounter();
    }

    private void addCounterToHashMap( HashMap<String,AtomicLong> hashMap, String key )
    {
        if( !hashMap.containsKey( key ) )
        {
            hashMap.put( key, new AtomicLong( 0 ) );
        }
    }

    public void addAsRirCounter( String rir )
    {
        addCounterToHashMap( asRirHits, rir );
    }

    public void addIp4RirCounter( String rir )
    {
        addCounterToHashMap( ip4RirHits, rir );
    }

    public void addIp6RirCounter( String rir )
    {
        addCounterToHashMap( ip6RirHits, rir );
    }

    public void addEntityRirCounter( String rir )
    {
        addCounterToHashMap( entityRirHits, rir );
    }

    public void addDomainRirCounter( String rir )
    {
        addCounterToHashMap( domainRirHits, rir );
    }
}
