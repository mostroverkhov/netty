/*
 * Copyright 2019 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.testsuite.transport;

import io.netty.channel.AbstractSingleThreadEventLoopTest;
import io.netty.channel.Channel;
import io.netty.channel.DefaultSelectStrategyFactory;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.EventLoopTaskQueueFactory;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorChooserFactory;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.RejectedExecutionHandlers;
import io.netty.util.concurrent.ThreadPerTaskExecutor;

import java.nio.channels.spi.SelectorProvider;

public class NioEventLoopTest extends AbstractSingleThreadEventLoopTest {

    @Override
    protected EventLoopGroup newEventLoopGroup() {
        return new NioEventLoopGroup();
    }

    @Override
    protected EventLoopGroup newEventLoopGroup(EventLoopTaskQueueFactory taskQueueFactory,
                                               EventLoopTaskQueueFactory tailTaskQueueFactory) {
        return new NioEventLoopGroup(
                0,
                new ThreadPerTaskExecutor(new DefaultThreadFactory("nio-test-pool")),
                DefaultEventExecutorChooserFactory.INSTANCE,
                SelectorProvider.provider(),
                DefaultSelectStrategyFactory.INSTANCE,
                RejectedExecutionHandlers.reject(),
                taskQueueFactory, tailTaskQueueFactory);
    }

    @Override
    protected Channel newChannel() {
        return new NioSocketChannel();
    }

    @Override
    protected Class<? extends ServerChannel> serverChannelClass() {
        return NioServerSocketChannel.class;
    }
}
