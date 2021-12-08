let api = [];
api.push({
    alias: 'OauthClientController',
    order: '1',
    link: '客户端管理controller',
    desc: '客户端管理Controller',
    list: []
})
api[0].list.push({
    order: '1',
    desc: '查询单个客户端',
});
api[0].list.push({
    order: '2',
    desc: '修改单个客户端',
});
api[0].list.push({
    order: '3',
    desc: '新增单个客户端',
});
api[0].list.push({
    order: '4',
    desc: '删除单个客户端',
});
api[0].list.push({
    order: '5',
    desc: '分页查询客户端',
});
api.push({
    alias: 'IndexController',
    order: '2',
    link: '',
    desc: '',
    list: []
})
api[1].list.push({
    order: '1',
    desc: '',
});
api.push({
    alias: 'TokenController',
    order: '3',
    link: '',
    desc: '',
    list: []
})
api[2].list.push({
    order: '1',
    desc: '认证页面',
});
api[2].list.push({
    order: '2',
    desc: '确认授权页面',
});
api[2].list.push({
    order: '3',
    desc: '退出',
});
api.push({
    alias: 'SentinelTestController',
    order: '4',
    link: '',
    desc: '',
    list: []
})
api[3].list.push({
    order: '1',
    desc: '',
});
api[3].list.push({
    order: '2',
    desc: '',
});
api.push({
    alias: 'UserController',
    order: '5',
    link: '用户controller',
    desc: '用户Controller',
    list: []
})
api[4].list.push({
    order: '1',
    desc: '查询登录用户',
});
api[4].list.push({
    order: '2',
    desc: '发送手机验证码',
});
api[4].list.push({
    order: '3',
    desc: '查询单个用户',
});
api[4].list.push({
    order: '4',
    desc: '修改单个用户',
});
api[4].list.push({
    order: '5',
    desc: '新增单个用户',
});
api[4].list.push({
    order: '6',
    desc: '删除单个用户',
});
api[4].list.push({
    order: '7',
    desc: '分页查询用户',
});
api.push({
    alias: 'dict',
    order: '6',
    link: 'dict_list',
    desc: '数据字典',
    list: []
})
document.onkeydown = keyDownSearch;
function keyDownSearch(e) {
    const theEvent = e;
    const code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code == 13) {
        const search = document.getElementById('search');
        const searchValue = search.value;
        let searchArr = [];
        for (let i = 0; i < api.length; i++) {
            let apiData = api[i];
            const desc = apiData.desc;
            if (desc.indexOf(searchValue) > -1) {
                searchArr.push({
                    order: apiData.order,
                    desc: apiData.desc,
                    link: apiData.link,
                    list: apiData.list
                });
            } else {
                let methodList = apiData.list || [];
                let methodListTemp = [];
                for (let j = 0; j < methodList.length; j++) {
                    const methodData = methodList[j];
                    const methodDesc = methodData.desc;
                    if (methodDesc.indexOf(searchValue) > -1) {
                        methodListTemp.push(methodData);
                        break;
                    }
                }
                if (methodListTemp.length > 0) {
                    const data = {
                        order: apiData.order,
                        desc: apiData.desc,
                        link: apiData.link,
                        list: methodListTemp
                    };
                    searchArr.push(data);
                }
            }
        }
        let html;
        if (searchValue == '') {
            const liClass = "";
            const display = "display: none";
            html = buildAccordion(api,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        } else {
            const liClass = "open";
            const display = "display: block";
            html = buildAccordion(searchArr,liClass,display);
            document.getElementById('accordion').innerHTML = html;
        }
        const Accordion = function (el, multiple) {
            this.el = el || {};
            this.multiple = multiple || false;
            const links = this.el.find('.dd');
            links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown);
        };
        Accordion.prototype.dropdown = function (e) {
            const $el = e.data.el;
            $this = $(this), $next = $this.next();
            $next.slideToggle();
            $this.parent().toggleClass('open');
            if (!e.data.multiple) {
                $el.find('.submenu').not($next).slideUp("20").parent().removeClass('open');
            }
        };
        new Accordion($('#accordion'), false);
    }
}

function buildAccordion(apiData, liClass, display) {
    let html = "";
    let doc;
    if (apiData.length > 0) {
        for (let j = 0; j < apiData.length; j++) {
            html += '<li class="'+liClass+'">';
            html += '<a class="dd" href="#_' + apiData[j].link + '">' + apiData[j].order + '.&nbsp;' + apiData[j].desc + '</a>';
            html += '<ul class="sectlevel2" style="'+display+'">';
            doc = apiData[j].list;
            for (let m = 0; m < doc.length; m++) {
                html += '<li><a href="#_' + apiData[j].order + '_' + doc[m].order + '_' + doc[m].desc + '">' + apiData[j].order + '.' + doc[m].order + '.&nbsp;' + doc[m].desc + '</a> </li>';
            }
            html += '</ul>';
            html += '</li>';
        }
    }
    return html;
}