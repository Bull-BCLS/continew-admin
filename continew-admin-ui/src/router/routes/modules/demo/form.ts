import { DEFAULT_LAYOUT } from '../../base';
import { AppRouteRecordRaw } from '../../types';

const FORM: AppRouteRecordRaw = {
  path: '/demo/form',
  component: DEFAULT_LAYOUT,
  meta: {
    locale: 'menu.form',
    icon: 'bookmark',
    requiresAuth: true,
    order: 901,
  },
  children: [
    {
      name: 'Step',
      path: 'step',
      component: () => import('@/views/demo/form/step/index.vue'),
      meta: {
        locale: 'menu.form.step',
        requiresAuth: true,
        roles: ['*'],
      },
    },
    {
      name: 'Group',
      path: 'group',
      component: () => import('@/views/demo/form/group/index.vue'),
      meta: {
        locale: 'menu.form.group',
        requiresAuth: true,
        roles: ['*'],
      },
    },
  ],
};

export default FORM;
