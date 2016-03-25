package org.kurron.example.core

import org.kurron.traits.GenerationAbility
import rx.Observable
import rx.Observer
import spock.lang.Specification

import java.time.Instant

/**
 * A learning test for the JavaRx APIs.
 **/
class RxJavaLearningTest extends Specification implements GenerationAbility {

    def processor = [onCompleted: { println 'completed' },
                     onError: { println 'error!' },
                     onNext: { println it } ] as Observer<Integer>


    def 'exercise manual observable with manual observer'() {
        given: 'an observable'
        def generator = { it.onNext( 40 ) ; it.onNext( 45 ) ; it.onCompleted() } as Observable.OnSubscribe<Integer>
        Observable<Integer> source = Observable.create( generator )

        when: 'we subscribe to the stream'
        source.subscribe( processor )

        then: 'we get expected results'
    }

    def 'exercise just observable with manual observer'() {
        given: 'an observable'
        Observable<Integer> source = Observable.just( 20, 30, 40, 50 )

        when: 'we subscribe to the stream'
        source.subscribe( processor )

        then: 'we get expected results'
    }

    def 'exercise deferred observable with manual observer'() {
        given: 'an observable'
        Observable<Instant> source = Observable.defer( { Observable.just( Instant.now() ).repeat( 6 ) } )

        when: 'we subscribe to the stream'
        def processor = [onCompleted: { println 'completed' },
                         onError: { println 'error!' },
                         onNext: { println it } ] as Observer<Instant>
        source.subscribe( processor )

        then: 'we get expected results'
    }

    def 'exercise composed operations'() {
        expect: 'we can compose all of these operations'
        Observable.range( 0, 100 )
                  .filter( { it % 2 == 0 } )
                  .map( { Integer.toHexString( it ).toUpperCase() } )
//                .doOnNext( { println it } )
                  .reduce( { a, b -> a + ', ' + b } )
                  .subscribe( { println it } )
    }
}
